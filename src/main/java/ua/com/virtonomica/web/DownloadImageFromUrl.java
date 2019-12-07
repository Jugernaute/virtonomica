package ua.com.virtonomica.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.entity.AbstractUnit;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.images.ProductImage;
import ua.com.virtonomica.entity.product.MainProduct;
import ua.com.virtonomica.entity.product.RetailProduct;
import ua.com.virtonomica.service.abstractUnit.AbstractUnitService;
import ua.com.virtonomica.service.company.company_products.CompanyProductsService;
import ua.com.virtonomica.service.images.ProductImagesService;
import ua.com.virtonomica.service.product.main_product.MainProductService;
import ua.com.virtonomica.service.product.retail_product.RetailProductService;
import ua.com.virtonomica.web.connection.Virtonomica;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DownloadImageFromUrl {
    private final Virtonomica virtonomica;
    private final AbstractUnitService<AbstractUnit> abstractUnitService;
    private final MainProductService mainProductService;
    private final RetailProductService retailProductService;
    private final CompanyProductsService companyProductsService;

    @Autowired
    public DownloadImageFromUrl(Virtonomica virtonomica, AbstractUnitService<AbstractUnit> abstractUnitService, MainProductService mainProductService, ProductImagesService productImagesService, RetailProductService retailProductService, CompanyProductsService companyProductsService) {
        this.virtonomica = virtonomica;
        this.abstractUnitService = abstractUnitService;
        this.mainProductService = mainProductService;
        this.retailProductService = retailProductService;
        this.companyProductsService = companyProductsService;
    }


    public void writeImages(){
        writeMainProductImages();
        writeRetailProductImages();
        writeFranchiseImages();
    }

    private void writeFranchiseImages() {
        WebClient webClient = virtonomica.getWebClient();
        HtmlPage imagePage;
        try {
            imagePage = webClient.getPage("https://virtonomica.ru/lien/main/franchise_market/list");
            List<HtmlTableRow> tableRows = getTableRows(imagePage);
            for (HtmlTableRow tableRow : tableRows) {
                HtmlTableCell cell = tableRow.getCell(2);
                DomElement firstElementChild = cell.getFirstElementChild();
                String src = firstElementChild.getAttribute("src");
                int firstIndex = src.lastIndexOf("/brand") + 1;
                int lastIndex = src.lastIndexOf(".");
                String symbol = src.substring(firstIndex, lastIndex);
                String franchiseName = cell.asText();
                franchiseImageSaver(franchiseName,src,symbol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void writeMainProductImages(){
        WebClient webClient = virtonomica.getWebClient();
        HtmlPage imagePage;
        try {
            imagePage = webClient.getPage("https://virtonomica.ru/lien/main/common/main_page/game_info/products");
            List<HtmlTableRow> tableRows = getTableRows(imagePage);

            for (HtmlTableRow htmlTableRow : tableRows) {
                Iterable<DomElement> childElements = htmlTableRow.getChildElements();
                for (DomElement childElement : childElements) {
                    String width = childElement.getAttribute("width");
                    if (!width.isEmpty()){
                        DomElement firstElementChild = childElement
                                .getFirstElementChild()
                                .getFirstElementChild();

                        String src = firstElementChild.getAttribute("src");
                        String title = firstElementChild.getAttribute("title");
//                        System.out.println(src +" "+ title);

                        mainProductWithImageSaver(title, src,title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRetailProductImages(){
        WebClient webClient = virtonomica.getWebClient();
        HtmlPage imagePage;
        try {
            imagePage = webClient.getPage("https://virtonomica.ru/lien/main/common/main_page/game_info/trading");
            List<HtmlTableRow> tableRows = getTableRows(imagePage);
            for (HtmlTableRow htmlTableRow : tableRows) {
                Iterable<DomElement> childElements = htmlTableRow.getChildElements();
                for (DomElement childElement : childElements) {
                    String width = childElement.getAttribute("width");
                    if (!width.isEmpty()){
                        DomElement firstElementChild = childElement
                                .getFirstElementChild()
                                .getFirstElementChild();

                        String src = firstElementChild.getAttribute("src");
                        String title = firstElementChild.getAttribute("title");
//                        System.out.println(src +" "+ title);

                        retailProductWithImageSaver(title, src,title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<HtmlTableRow> getTableRows(HtmlPage imagePage){
        HtmlTable table = (HtmlTable) imagePage.getByXPath("//table[@class='list']").get(0);
        List<HtmlTableRow> rows = table.getRows();
            return rows.stream()
                .filter(r -> r.getAttributesMap().keySet().contains("class"))
                .collect(Collectors.toList());
    }

    private void mainProductWithImageSaver(String nameOfMainProduct,String src,String title){
        List<MainProduct> mainProductList = new ArrayList<>();
        MainProduct mainProduct= mainProductService.findByName(nameOfMainProduct);
        mainProductList.add(mainProduct);
        String symbol = mainProduct.getSymbol();
        String path = miniPath(symbol);
        writeImage(src,fullPath(symbol));
        ProductImage productImage = new ProductImage(mainProduct.getId(), title, symbol, path);

        mainProduct.setProductImage(productImage);
        abstractUnitService.save(mainProduct);
        productImage.setMainProduct(mainProductList);
        abstractUnitService.save(productImage);
    }

    private void retailProductWithImageSaver(String nameOfRetailProduct, String src, String title) {
        List<RetailProduct>retailProductList = new ArrayList<>();
        RetailProduct retailProduct= retailProductService.findByName(nameOfRetailProduct);
        retailProductList.add(retailProduct);
        String symbol = retailProduct.getSymbol();
        String path = miniPath(symbol);
        writeImage(src,fullPath(symbol));

        ProductImage productImage = new ProductImage(retailProduct.getId(), title, symbol, path);
        retailProduct.setProductImage(productImage);

        abstractUnitService.save(retailProduct);
        productImage.setRetailProducts(retailProductList);
        abstractUnitService.save(productImage);
    }

    private void franchiseImageSaver(String franchiseName, String src, String symbol) {
        String path = miniPath(symbol);
        writeImage(src,fullPath(symbol));
        CompanyProducts companyProducts= companyProductsService.findBySymbol(symbol);
        if (!(companyProducts==null)) {
            ProductImage productImage = new ProductImage(companyProducts.getId(), franchiseName, symbol, path);
            abstractUnitService.save(productImage);
        }

//        retailProductList.add(retailProduct);
//        String symbol = retailProduct.getSymbol();

//        retailProduct.setProductImage(productImage);

//        abstractUnitService.save(retailProduct);
//        productImage.setRetailProducts(retailProductList);
    }

    private static String miniPath (String name){
//        /img/main_product/alarm.gif
        String miniPath = "" +
                File.separator+
                "img"+
                File.separator+
                "products"+
                File.separator+
                name;

       return new File(
                miniPath+
                ".gif").toString();
    }

    private static String fullPath(String name){
        return new File(System.getProperty("user.dir")+
                File.separator+
                "src"+
                File.separator+
                "main"+
                File.separator+
                "resources"+
                File.separator+
                "static"+ miniPath(name)
        ).toString();
    }

    private void writeImage(String src, String pathname){
        try {
            URL url = new URL("https://virtonomica.ru"+src);
            // read the url
//            System.out.println(url.toString());
            BufferedImage image = ImageIO.read(url);
//            for gif
            ImageIO.write(image, "gif",new File(pathname));
        } catch (IOException e) {
            System.out.println("Error load image");
            e.printStackTrace();
        }
    }




}
