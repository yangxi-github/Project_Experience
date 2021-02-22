package ws.restful.resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author chenli
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.resources.CategoryResource.class);
        resources.add(ws.restful.resources.CosFilter.class);
        resources.add(ws.restful.resources.CustomerResource.class);
        resources.add(ws.restful.resources.GameResource.class);
        resources.add(ws.restful.resources.HardwareResource.class);
        resources.add(ws.restful.resources.OtherSoftwareResource.class);
        resources.add(ws.restful.resources.ProductResource.class);
        resources.add(ws.restful.resources.PromotionResource.class);
        resources.add(ws.restful.resources.SaleTransactionResource.class);
        resources.add(ws.restful.resources.TagResource.class);

    }

}
