# SHOPPING CART

## REQUIREMENTS
* Java 8
* SpringBoot
* Maven and Lombok
* Mockito for Tests

## Installation

```
  > git pull git@github.com:shubha26/OnlineStore.git
  > cd OnlineStore
  > mvn clean install
```

## Execution

```
This is a springboot application which runs on 8081 port. 
Just import into IDE and run the app.
StoreApplication.java has the main method.
```


## Introduction

The application is a simple online store where  admin/user can perform certain operations via Rest APIs.

#### Admin APIs : add/remove a product, add a discount to a product

Samples

* Add/Create a Product in the store : (HttpPost)
```
URL : http://localhost:8081/admin/products/

Body : {
"name":"HeadPhones",
"price":"20.00",
"info":"Business Meetings",
"stockQuantity":"500"
}
```
This will add a product with the following attributes in the input body of the Post request.

-------------------------------------

* Add a Discount for a Product : (HttpPost)

```
URL : http://localhost:8081/admin/discount/{discountName}/products/{productId}
Ex :  http://localhost:8081/admin/discount/Buy1Get50Off/products/2
```
This will add the **Buy1Get50Off** as the discount available for the product with Id 2.
So for two same products purchased, the second item would be at 50% of the price.

-------------------------------------


#### User APIs : add/remove to/from a cart, get Receipt of the items

Samples

* Add/Create a Product in the store : (HttpPost)
```
URL : http://localhost:8081/user/cart/{productId}/{quantity}
Ex  : http://localhost:8081/user/cart/1/2
```
This will add 2 item of product with product id as 1.

-------------------------------------

* Generate the Receipt for the cart Items 

```
URL : http://localhost:8081/user/cart/receipt
Ex Output
{
    "totalPrice": 300,
    "finalDiscountedPrice": 280,
    "itemsPurchased": {
        "Product{name='Table', price=100.0, info='Wooden Study Table'}": 2,
        "Product{name='HeadPhones', price=20.0, info='Business Meetings'}": 5
    }
}
```
The receipt has details about the products with the total value and the value after discount.
Here the discount was added(**Buy1Get50Off**) on Headphones.

-------------------------------------
## Testing

The rest APIs are tested using JUnit and Mockito. MockMVC is used to Mock the Web layer, So tests can be executed standalone also.
The unit tests and some integration tests are added. The **ProductTests.java** and **CartTests.java** are the main testing classes.

## TODOs
1. Validations should be added.
2. Appropriate ExceptionHandling and HTTP Status Codes
3. A lot can be added :) 

## Enhancement
1. The  functionality with multiple discounts can be added for a single product.
2. Proper Persistence should be used. 
3. A lot ;)




