# StockHawk-1
An Android app for monitoring stocks. On launche this App will show a set of 4 common stocks with their current value and % change. A floating "+" button allows for the addition of new stocks, and swiping a current stock will remove it from the stock list. Clicking the stock on the other hand will show a graph of that stock for the past month. An App widgit is available that can show the stocks on the home page without the App being launched. The stock prices are updated once an hour.

The following libraries were used: 

com.melnykov:floatingactionbutton:1.2.0

com.github.PhilJay:MPAndroidChart:v2.2.5

com.squareup.retrofit2:retrofit:2.0.0-beta4

# Run Instructions
clone the repo: git clone https://github.com/Alz-Android/StockHawk-1.git

Open Android Studio and select Import Project

Select the file build.gradle in the root of the cloned repo

Select Run -> Run 'app' (or Debug 'app') from the menu bar

Select the device you wish to run the app on and click 'OK'
