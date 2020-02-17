# ImageProcessingSpringAPI
Aplikacja do zdalnej edycji obrazów z wykorzystaniem Spring boot.

----
     POST /image 
 przyjmuje obraz w formacie binarnym i zapisuje go do pamięci aplikacji, zwraca unikalny identyfikator
 
     DELETE /image/{id}
usuwa obraz o danym identyfikatorze {id} z pamięci aplikacji
     
     GET /image/{id}/size
zwraca dane w formacie JSON z wymiarami wczytanego obrazu
     
     GET /image/{id}/histogram
zwraca dane w formacie JSON zawierające histogram obrazu
     
     GET /image/{id}/crop 
zwraca wycinek obrazu – parametry wycinanego fragmentu (start, stop, szerokość, wysokość) należy również przesłać do routingu

