# File Server
> This code part can help store file of small size in mongodb database.

### How to run
* Run mongodb in background
* Run gradlew by command `gradlew bootRun`

### API list
* GET /files/{pageIndex}/{pageSize} : Paging query file list
* GET /files/{id} : Download file
* GET /view/{id} : View file online
* POST /upload : Upload file
* DELETE /{id} : Delete file