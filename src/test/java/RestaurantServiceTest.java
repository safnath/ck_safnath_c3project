import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service;
    Restaurant restaurant;
    static LocalTime openingTime;
    static LocalTime closingTime;
    String restaurantName;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeAll
    public static void prepare(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
    }

    @BeforeEach // No need for after each to reset, as this automatically resets the values in 1st three statements
    public void setUp(){
        service = new RestaurantService();
        restaurantName = "Amelie's cafe";
        restaurant = service.addRestaurant(restaurantName,"Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        assertDoesNotThrow(()->{service.findRestaurantByName(restaurantName);});
        // Updated global restaurant value, but it will be reset in After and Before functions
        restaurant = service.findRestaurantByName(restaurantName);
        assertNotNull(restaurant);
        assertEquals(restaurantName, restaurant.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        String illegalRestaurantName = "abcd street food center";
        restaurantNotFoundException ex = assertThrows(restaurantNotFoundException.class, ()->{
            service.findRestaurantByName(illegalRestaurantName);
        });
        // Because abcd is not a restaurant
        assertEquals(illegalRestaurantName, ex.getMessage());
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant(restaurantName);
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}