# Work and Travel Society App

### WATS API ENDPOINTS:

`POST` /api/public/login ; request body: {"username":"", "password":""}

#### Reviews

`GET` /api/public/locations/{locationId}/reviews/{reviewId}  
`GET` /api/public/locations/{locationId}/reviews?page=0&size=20&sort=id,asc  

`GET` /api/public/locations/{locationId}/reviews/{reviewId}/comments?page=0&size=20&sort=id,asc  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/top-comments?limit=3
    
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/likes    
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/comments/{commendId}/likes  

`POST` /api/locations/{locationId}/reviews ; request body: {"description":""}
`POST` /api/locations/{locationId}/reviews/{reviewId}/comments ; request body: {"description":""}   
`POST` /api/locations/{locationId}/reviews/{reviewId}/likes  
`POST` /api/locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes    

#### Forum
`GET` /api/public/locations/{locationId}/forum/questions/{questionId}      
`GET` /api/public/locations/{locationId}/forum/questions?page=0&size=20&sort=id,asc    

`GET` /api/public/locations/{locationId}/forum/questions/{questionId}/answers?page=0&size=20&sort=id,asc      
`GET` /api/public/locations/{locationId}/forum/questions/{questionId}/top-answers?limit=3  

`GET`  /api/public/locations/{locationId}/forum/questions/{questionId}/answers/{answerId}/likes  

`POST` /api/locations/{locationId}/forum/questions ; request body: {"question":""}    
`POST` /api/locations/{locationId}/forum/questions/{questionId}/answers ; request body: {"answer":""}      
`POST` /api/locations/{locationId}/forum/questions/{questionId}/answers/{answerId}/likes  
  
  
