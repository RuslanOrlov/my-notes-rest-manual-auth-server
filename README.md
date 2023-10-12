# my-notes-rest-manual-auth-server
EN: A Java and Spring Boot project for taking notes using the REST API (only the server side are implemented in this application). 

The project implements the REST API, which is developed manually in the form of a Rest controller and provides endpoints for the Note entity. At the same time:
1) REST endpoints provide a complete list of data operations (CRUD);
2) implemented a logical deletion operation that changes the value of the IsDeleted field of the note object;
3) physical deletion, as one of the CRUD operations, is only available for note objects with the true value of the IsDeleted field;

NOTE: In this part, this project is similar to the project from the repository "my-notes-rest-manual-auth" (only in the server part).

In addition, the application has developed authentication and configured security based on Spring Security, which supports:
1) Http Basic user authentication in the server part of the application by transmitting user authentication data in the "Authorization" header in client requests;
2) CSRF protection of the server part of the application by using a CSRF token, which the client requests from the server and is then transmitted in the header "X-CSRF-TOKEN" in client requests.

P.S.: This version of the application implements only the functions of the server.

/-------------------------------------------------------------------------------------------------------------------------------/

RU: Проект на Java и Spring Boot по учету заметок с использованием REST API (в этом приложении реализована только сторона сервера).

Проект реализует REST API, который разработан вручную в виде Rest контроллера и предоставляет конечные точки для сущности Note (Заметка). При этом:
1) конечные точки REST предоставляют полный перечень операций с данными (CRUD); 
2) реализована операция логического удаления, которая изменяет значение поля isDeleted объекта заметки; 
3) физическое удаление, как одна из операций CRUD, доступна только для объектов заметок с истинным значением поля isDeleted; 

ПРИМЕЧАНИЕ: В этой части данный проект аналогичен проекту из репозитория "my-notes-rest-manual-auth" (только в части сервера).

Кроме того, в приложении разработана аутентификация и настроена безопасность на основе Spring Security, которая поддерживает: 
1) Http Basic аутентификацию пользователя в серверной части приложения путем передачи аутентификационных данных пользователя в заголовке "Authorization" в запросах клииента; 
2) CSRF защиту серверной части приложения путем использования CSRF токена, который клиент запрашивает с сервера и далее передается в заголовке "X-CSRF-TOKEN" в запросах клиента. 

P.S.: Данная версия приложения реализует только функции сервера.
