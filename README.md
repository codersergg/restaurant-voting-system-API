# Restaurant voting system

REST API application using Spring-Boot/Hibernate/SpringMVC without an interface.

**Project description:**<br>
A voting system for deciding where to have lunch.<br>
There are two types of users: administrators and regular users.
The administrator can enter the restaurant and its lunch menu of the day (usually 2-5 dishes, only the name of the dish and the price)
The menu changes every day (updates are made by administrators)
Users can vote for which restaurant they want to have lunch at
Only one vote is counted for each user.<br>

If the user votes again on the same day:<br>
&emsp;If this happens before 11: 00, we assume that he has changed his mind.<br>
&emsp;If it is after 11: 00, then it is too late, the vote cannot be changed.<br>
Each restaurant offers a new menu every day.

[REST API documentation](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

[POSTMAN](http://app.getpostman.com/join-team?invite_code=52e5ee52d5ce4e719d88f240bb8919a9&ws=789aa5cd-5eab-4d38-8b77-3e61f9c8bb67)

## Сredentials:

<table>
<tr><th>Login</th><th>Password</th></tr>
<tr><td>admin@codersergg.com</td><td>admin</td></tr> 
<tr><td>user1@gmail.com</td><td>password</td></tr> 
<tr><td>user2@gmail.com</td><td>password</td></tr> 
<tr><td>user3@gmail.com</td><td>password</td></tr> 
</table>