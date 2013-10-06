package lab03_guessNumGame;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

// or import javax.faces.bean.ManagedBean;

@ApplicationScoped
public class RandomNumberGenerator implements Serializable {

    private Random random = new Random(System.currentTimeMillis());
    
    @Produces
    @Named
    @RandomNum
    int getRandomNumber() {
        return random.nextInt(100);
    }

}