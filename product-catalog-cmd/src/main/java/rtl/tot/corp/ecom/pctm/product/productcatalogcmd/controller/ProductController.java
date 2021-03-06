package rtl.tot.corp.ecom.pctm.product.productcatalogcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.application.adapters.CreateProductCommandBus;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.application.adapters.CreateProductCommandImpl;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.application.adapters.DecoratorCreateProductCommandBus;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.domain.ports.CreateProductCommand;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.infraestructure.adapters.http.rest.constants.RestConstants;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.infraestructure.adapters.http.rest.domain.APIResponse;
import rtl.tot.corp.ecom.pctm.product.productcatalogcmd.infraestructure.adapters.http.rest.domain.Product;

@RestController
@Api(value = "ORION", description = "ORION Prouct Management API")
@Slf4j
public class ProductController {

	@Autowired
	DecoratorCreateProductCommandBus cmdBus;

	@RequestMapping(path = "/MREX/PRMG/v1.0/PRODUCT", method = POST)
	@ApiOperation(value = "Add Product", response = APIResponse.class)
	public ResponseEntity<APIResponse> createProduct(@RequestBody Product request) {

		// E2EContext e2e = new E2EContext();
		// try {
		// e2e.setE2EContext(headers);
		// } catch (E2EHelperNotFoundException e) {
		// log.error("Error E2EContext setting headers");
		//
		// }
		// e2e.setServiceRef("Appointment");

		log.info("Create Product request.", request);
		try {

			CreateProductCommandImpl cmd = new CreateProductCommandImpl(request);

			if (cmdBus.execute(cmd))
				log.info("Product Created successful ", request.getSku());
			else{
				log.info("Product not Created ", request.getSku());
				return new ResponseEntity<APIResponse>(this.buildErrorRes("Product not Created"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			log.debug("Product Created Exception ", request.getSku());
			return new ResponseEntity<APIResponse>(this.buildErrorRes(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<APIResponse>(this.buildSuccessRes("Product Created"), HttpStatus.OK);
	}

	/**
	 * API success response
	 *
	 * @return
	 */

	private APIResponse buildSuccessRes(String msg) {
		APIResponse res = new APIResponse();
		res.setCode(RestConstants.SUCCESS_CODE);
		res.setType(RestConstants.SUCCESS_RESPONSE);
		res.setMessage(msg);
		return res;
	}

	/**
	 * API Error response
	 *
	 * @return
	 */
	private APIResponse buildErrorRes(String error) {
		APIResponse res = new APIResponse();
		res.setCode(RestConstants.ERROR_CODE);
		res.setType(RestConstants.SYSTEM_ERROR);
		res.setMessage(error);
		return res;
	}

}