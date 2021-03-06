package rtl.tot.corp.ecom.pctm.product.productcatalogcmd.application.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.domain.model.ProductAggregate;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.domain.model.ProductService;

@Service
public class ProductServiceApplicationImpl {

	@Autowired
	ProductService service;
	ProductAggregate aggregate;

	public boolean addProduct(CreateProductCommandImpl cmd) {
		
		aggregate = new  ProductAggregate.Builder()
				.sku(cmd.getSku())
				.build();
		if (this.aggregate.addProduct(service))
			return true;
		else
			return false;

	}

}
