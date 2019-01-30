package rtl.tot.corp.ecom.pctm.product.productcatalogcmd.domain.eda.integration;

public enum EventType {
    PRODUCT_CREATED("productCreated"),
    PRODUCT_UPDATED("productUpdated"),
    PRODUCT_DELETED("productDeleted");

    private final String name;

    EventType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
