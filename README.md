# Work and Travel Society App

### WATS API ENDPOINTS:

`POST` /api/public/login ; request body: {"username":"", "password": ""}

`GET` /api/public/locations/{locationId}/reviews/{reviewId}  
`GET` /api/public/locations/{locationId}/reviews  

`GET` /api/public/locations/{locationId}/reviews/{reviewId}/comments  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/top-comments?limit
  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/likes/count  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/likes  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/comments/{commendId}/likes/count  
`GET` /api/public/locations/{locationId}/reviews/{reviewId}/comments/{commendId}/likes  

`POST` /api/locations/{locationId}/reviews  
`POST` /api/locations/{locationId}/reviews/{reviewId}/comments  
`POST` /api/locations/{locationId}/reviews/{reviewId}/likes  
`POST` /api/locations/{locationId}/reviews/{reviewId}/comments/{commentId}/likes  
