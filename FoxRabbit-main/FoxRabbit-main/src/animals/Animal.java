
package animals;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        this.location = location;
        field.place(this, location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * gets the animal's age.
     * @return the animal's age.
     */
    protected int getAge()
    {
        return age;
    }

    /**
     * sets the animal's age.
     * @param age the animal's age.
     */
    protected void setAge(int age)
    {
        this.age = age;
    }

    /**
     * increases the age and kills if too old.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * checks if the animal can breed.
     * @return true if can breed.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }

    /**
     * calculates how many babies to have.
     * @return number of births.
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * gets the max age for this animal.
     * @return the max age.
     */
    abstract protected int getMaxAge();

    /**
     * gets the breeding age for this animal.
     * @return the breeding age.
     */
    abstract protected int getBreedingAge();

    /**
     * gets the breeding chance for this animal.
     * @return the breeding probability.
     */
    abstract protected double getBreedingProbability();

    /**
     * gets the max number of babies for this animal.
     * @return the max litter size.
     */
    abstract protected int getMaxLitterSize();

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
