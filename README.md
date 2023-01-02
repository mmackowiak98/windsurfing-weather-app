# windsurfing-weather-app
Builded with - Java 17, Spring Boot 3.0, Lombok, H2, JSON,  

## App that gives you best location to go surfing :) 
This application comes with : 
 - Calculation formula that gives you best weather conditions,
 - 5 locations out of the box to choose from
 - 16 days weather forecast for every location

Besides that you can : 
 - Add your own location to check the weather on 
 
 # How to start it 
 Pull repository to your own machine, open it in your IDE and run `WindsurfingWeatherApplication`
 
 There are 3 endpoints that you can access, you can do it as seen on a screenshots : 
  - GET - `/api/weather/best` - takes Date in YYYY-MM-DD format as a JSON
  
    ![get-best](https://user-images.githubusercontent.com/46621470/210277966-d00f5632-d6b1-4073-a2b5-32e53ab28f6f.PNG)

  - POST - `/api/weather/add` - takes url from `www.weatherbit.io` of a location you want to add as a JSON
  
    ![post-add](https://user-images.githubusercontent.com/46621470/210278030-0714ebd7-3285-412e-a770-ae1e59039bcc.PNG)

  - GET - `/api/weather/all` - Showing all availalble locations that gonna be used when searching for best one
  
    ![get-all](https://user-images.githubusercontent.com/46621470/210278135-f0f45b3b-3201-48fe-ae5c-996a55ba86cf.PNG)
