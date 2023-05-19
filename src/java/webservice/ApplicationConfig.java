package webservice;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webservice.AccessWebService.class);
        resources.add(webservice.NoteWebService.class);
        resources.add(webservice.NotebookWebService.class);
        resources.add(webservice.PriorityWebService.class);
        resources.add(webservice.UserWebService.class);
    }
    
}