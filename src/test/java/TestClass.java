import POJOs.Category;
import POJOs.Pet;
import POJOs.Tag;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class TestClass extends BaseTest
{
    protected RequestSpecification request = null;
    int petId;

    @BeforeMethod
    public void setup()
    {
        request = RestAssured.given();
        request.header("content-type","application/json");
        request.urlEncodingEnabled(false);

        petId = CreatePets();
    }

    @Test
    public void GetPet()
    {
        System.out.println("Pet Id: " + petId);
        Response response = request.pathParam("petId",petId).get("/pet/{petId}");
        Assert.assertEquals(response.statusCode(), 200);

        Pet pet = response.getBody().as(Pet.class);
        Assert.assertTrue(pet.id == petId);
    }

    @Test
    public void CreatePet()
    {
        Pet newPet = new Pet(petId, new Category(1, "A"), "dog", Arrays.asList("google.com"),
                Arrays.asList(new Tag(12,"Wild")), "Sold");

        request.body(newPet);
        Response response = request.post("/pet");
        Assert.assertEquals(response.statusCode(), 200);

        Pet pet = response.getBody().as(Pet.class);
        Assert.assertTrue(pet.id == petId);
    }

    @Test
    public void UpdatePet()
    {
        Pet newPet = new Pet(petId, new Category(1, "A"), "dog1", Arrays.asList("google.com"),
                Arrays.asList(new Tag(12,"Wild")), "Sold");

        request.body(newPet);
        Response response = request.put("/pet");
        Assert.assertEquals(response.statusCode(), 200);

        Pet pet = response.getBody().as(Pet.class);
        Assert.assertTrue(pet.name.equals("dog1"));
    }

    @Test
    public void DeletePet()
    {
        Response response = request.pathParam("petId",petId).delete("/pet/{petId}");
        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertTrue(request.pathParam("petId",petId).get("/pet/{petId}").getStatusCode() == 404);
    }

    private int CreatePets()
    {
        int[] input = IntStream.range(100, 200).toArray();
        int id = input[new Random().nextInt(input.length)];

        Pet newPet = new Pet(id, new Category(1, "A"), "dog", Arrays.asList("google.com"),
                Arrays.asList(new Tag(12,"Wild")), "Sold");

        Response response = request.body(newPet).post("/pet");
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getBody().asString());
        return response.getBody().as(Pet.class).id;
    }
}
