- project name: shopping-web-java
- content: Trang bán hàng thương mại điện tử cho phép người dùng mua và tạo đơn
hàng và trang quản lí cho tài khoản có quyền quản trị admin quản lí, theo dõi đơn hàng, sản phẩm và người dùng.
+ Technical: 
- Spring Boot
- Rest full API 
- Java8
- Spring Security
- RDBMS postgres
- Template admin Lte 
- Thymeleaf




** product run instructions:
- Download and setting postgres database or mysql database.
- Create new database  name = shopp_cart.

+ tools : download and setting intellij: 
- download and setting java version 11 or 16.
- download and setting maven >= 3.6.3.
- import project to intellij.

<img width="1063" alt="Screen Shot 2021-11-21 at 21 04 29" src="https://user-images.githubusercontent.com/80323466/142765005-bcce3912-f2e2-422c-9ed4-8459814ef5af.png">

- Config connection to database in src/main/resources/application.properties

<img width="1123" alt="Screen Shot 2021-11-21 at 20 50 23" src="https://user-images.githubusercontent.com/80323466/142764511-59533b8b-1ad4-4bea-b025-319206631f02.png">



-add new dependency for postgresql in file  pom.xml nếu không tìm thấy:
<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>


- run project:
  open file ShoppingApplication.java and press the button same picture

![image](https://user-images.githubusercontent.com/80323466/142765073-554a30d7-47da-4ba6-9018-bbf5d891f788.png)

or :  open terminal run:    mvn spring-boot:run


BUILD SUCCESS:

![image](https://user-images.githubusercontent.com/80323466/142765328-48e64996-7014-4993-877d-465fdd49f214.png)


- open browser and input url :  http://localhost:8088

<img width="1716" alt="Screen Shot 2021-11-21 at 21 18 10" src="https://user-images.githubusercontent.com/80323466/142765476-54967ca9-48c5-4a72-bc30-f2ebb8ccf0a2.png">


**structure project

1.database

<img width="985" alt="Screen Shot 2021-11-20 at 14 10 22" src="https://user-images.githubusercontent.com/80323466/142735613-4962ae80-43f8-4281-b642-946f67a1685d.png">



2.structure project

<img width="610" alt="Screen Shot 2021-11-21 at 00 27 39" src="https://user-images.githubusercontent.com/80323466/142735656-167191ec-958b-4067-89dc-0ee41d0212ab.png">


3. detail structure backend
 
 <img width="668" alt="Screen Shot 2021-11-21 at 00 30 02" src="https://user-images.githubusercontent.com/80323466/142735742-9ed814c1-465d-40e4-a3a3-510e2c2401d0.png">



4. detail structure fontend

 <img width="501" alt="Screen Shot 2021-11-21 at 00 32 24" src="https://user-images.githubusercontent.com/80323466/142735820-9af80f8b-979d-4104-92a3-1df1909eda39.png">
