Feature: Amazon Ecommerce website

Scenario: User Signin and Search for a product and Add it to cart

Given Launch the Application
And Check for list of broken links
And Click on signin button
When Enter the email and Click on Continue
And Enter Password and Click on SignIn
Then User loggedIn Successfully

When User Search for product "iphone 16"
Then User clicks on Add to cart