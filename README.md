# Task in original
#### Система Библиотека. Создайте Каталог, по которому можно искать по:
* Автору (одному из группы).
* Названию книги или её фрагменте.
* Одному из ключевых слов книги (атрибут книги).
#### Каталог книг заполняет Администратор, добавляя и изменяя/удаляя их.
#### Каждая книга должна иметь адрес (место на полке) или читателя.
#### Читатель чтобы взять книгу регистрируется, оставляя э-мейл и номер телефона.
#### Книга может быть взята у Администратора в библиотеке на время не более месяца,
#### только в случае если книга доступна в библиотеке.
#### Администратор должен иметь страницу где отражаются взятые книги и читатели, которые пользуются книгой.
## Installation instructions
* Download tomcat server. More info by this link: 
##### https://tomcat.apache.org/download-80.cgi
* Download source code using git clone. Visit official documentation site:
##### https://git-scm.com/docs/git-clone
## Run program instructions
* Copy the WAR file into $CATALINA_HOME\webapps directory.
Your downloaded archive -> apache-tomcat-[version] -> webapps.
* Restart the server. Whenever Tomcat is started,
 it will unpack the WAR file it found in the webapps directory and launch the application in that manner.
 Open terminal in deirectory /bin(part of path showed before) and run this command:
 ##### - startup
 For a stop running program - run following command:
 ##### - shutdown
## Same task using Spring Boot by this link:
### https://github.com/KySo4oK/e-lib-Spring-
