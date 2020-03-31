package ua.com.virtonomica.service.unit_wrapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.virtonomica.entity.company.CompanyUnits;
import ua.com.virtonomica.service.company.company_units.MyCompanyUnitsService;
import ua.com.virtonomica.service.geography.wrappers.GeographyWrapperService;
import ua.com.virtonomica.service.industrial.wrappers.IndustrialWrapperService;
import ua.com.virtonomica.service.product.main_product.wrappers.MainProductWrapperService;
import ua.com.virtonomica.service.product.retail_product.wrappers.RetailProductWrapperService;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;
import ua.com.virtonomica.utils.create.industrial.IndustrialWrapper;
import ua.com.virtonomica.utils.create.my_company_units.MyCompanyUnitsWrapper;
import ua.com.virtonomica.utils.create.product.main_product.MainProductWrapper;
import ua.com.virtonomica.utils.create.product.retail_product.RetailProductWrapper;
import ua.com.virtonomica.utils.create.IUnitWrapper;

@Service
@Transactional
public class UnitWrapperTransaction implements UnitWrapperService {
private final IndustrialWrapperService industrialWrapperService;
private final GeographyWrapperService geographyWrapperService;
private final MainProductWrapperService mainProductWrapperService;
private final RetailProductWrapperService retailProductWrapperService;
private final MyCompanyUnitsService myCompanyUnitsService;



    public UnitWrapperTransaction(IndustrialWrapperService industrialWrapperService, GeographyWrapperService geographyWrapperService, MainProductWrapperService mainProductWrapperService, RetailProductWrapperService retailProductWrapperService, MyCompanyUnitsService myCompanyUnitsService) {
        this.industrialWrapperService = industrialWrapperService;
        this.geographyWrapperService = geographyWrapperService;
        this.mainProductWrapperService = mainProductWrapperService;
        this.retailProductWrapperService = retailProductWrapperService;
        this.myCompanyUnitsService = myCompanyUnitsService;
    }

    @Override
    public void save(IUnitWrapper iUnitWrapper) {
        Class<? extends IUnitWrapper> wrapperClass = iUnitWrapper.getClass();
//        System.out.println(wrapperClass.getName());
        if (wrapperClass.equals(IndustrialWrapper.class)){
           industrialWrapperService.save(((IndustrialWrapper) iUnitWrapper));
        } else if ( wrapperClass.equals(GeographyWrapper.class)){
            geographyWrapperService.save(((GeographyWrapper) iUnitWrapper));
        }else if (wrapperClass.equals(MainProductWrapper.class)) {
            mainProductWrapperService.save(((MainProductWrapper) iUnitWrapper));
        }else if (wrapperClass.equals(RetailProductWrapper.class)){
            retailProductWrapperService.save(((RetailProductWrapper) iUnitWrapper));
        } else if (wrapperClass.equals(MyCompanyUnitsWrapper.class)){
            CompanyUnits companyUnits = new CompanyUnits(((MyCompanyUnitsWrapper) iUnitWrapper));
            myCompanyUnitsService.save(companyUnits);
        }

    }


}
