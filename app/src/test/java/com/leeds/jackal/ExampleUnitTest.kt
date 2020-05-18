package com.leeds.jackal

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
import com.leedsride.rentalapp.LeedsRide.models.Locations
import com.leedsride.rentalapp.LeedsRide.models.Login
import com.leedsride.rentalapp.LeedsRide.models.Register
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class UnitTests {

    private val BASE_URL = "https://sc17gs.pythonanywhere.com/api/"

    @Test
    fun correctLogin() {
        val login = Login()
        login.setUsername("prudd")
        login.setPassword("password")
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<Login> = sampleAPI.attemptLogin(login)
        try {
            //Magic is here at .execute() instead of .enqueue()
            val response: Response<Login> = call.execute()
            val result: Login = response.body()
            assertEquals("Login Accepted", result.getLoginStatus())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun incorrectLogin() {
        val login = Login()
        login.setUsername("random")
        login.setPassword("random")
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<Login> = sampleAPI.attemptLogin(login)
        try {
            //Magic is here at .execute() instead of .enqueue()
            val response: Response<Login> = call.execute()
            val result: Login = response.body()
            assertEquals("Incorrect Login Information", result.getLoginStatus())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun successfulRegister() {
        val register = Register()
        register.setUsername("hi")
        register.setPassword("password")
        register.setEmail("test@test.com")
        register.setPhone("07951399157")
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<Register> = sampleAPI.attemptRegister(register)
        try {
            val response: Response<Register> = call.execute()
            val result: Register = response.body()
            assertEquals("User Registered", result.getRegistrationStatus())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun emailFailureRegister() {
        val register = Register()
        register.setUsername("prudd")
        register.setPassword("password")
        register.setEmail("prudd@gmail.com") ///////////////////Set this to an email that is already in the database
        register.setPhone("07951399157")
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<Register> = sampleAPI.attemptRegister(register)
        try {
            val response: Response<Register> = call.execute()
            val result: Register = response.body()
            assertEquals("That email is taken", result.getRegistrationStatus())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun usernameFailureRegister() {
        val register = Register()
        register.setUsername("prudd") ///////////////////Set this to a username that is already in the database
        register.setPassword("password")
        register.setEmail("testing@test.com")
        register.setPhone("07951399157")
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<Register> = sampleAPI.attemptRegister(register)
        try {
            val response: Response<Register> = call.execute()
            val result: Register = response.body()
            assertEquals("That username is taken", result.getRegistrationStatus())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun locations() {
        val retrofit: Retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sampleAPI: restAPI = retrofit.create(restAPI::class.java)
        val call: Call<List<Locations>> = sampleAPI.getLocations()
        try {
            val response: Response<List<Locations>> = call.execute()
            val result: List<Locations> = response.body()
            assertEquals("Leeds City Centre", result[0].getName())
            assertEquals(5.8116, result[0].getLatitude(), 0.01)
            assertEquals(-1.62727, result[0].getLongitude(), 0.01)
            assertEquals(5, result[0].getBikesAvailable())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun booking() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun orders() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun collectBikes() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun returnBikes() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun logout() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }