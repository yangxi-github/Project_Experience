package jsf.managedbean;

import entity.Game;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;


@Named(value = "viewGameManagedBean")
@Dependent
public class ViewGameManagedBean implements Serializable {

    private Game gameToView;

    public ViewGameManagedBean() {
        gameToView = new Game();
    }

    @PostConstruct
    public void postConstruct() {
    }

    public Game getGameToView() {
        return gameToView;
    }

    public void setProductEntityToView(Game gameToView) {
        this.gameToView = gameToView;
    }
}
