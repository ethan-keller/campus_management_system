User(username, password, type)\
-  Username: String\
-  Password: String (User password is not stored as plain text)\
-  Type: int\
o  0: admin\
o  1: teacher\
o  2: student\

Reservations(id, username, room, date, startingTime, endingTime)\
-  Id: integer\
-  Username: String (Same as User's username)\
-  Room: int (Room id)\
-  Date: Date (String ('yyyy-mm-dd'))\
-  StartingTime: String 'hh:mm:ss'\
-  EndingTime: String 'hh:mm:ss'\

Room(id, name, building, teacherOnly, capacity, photos, description)\
-  Id: int\
-  Name: String\
-  Building: int (Building id)\
-  TeacherOnly: Boolean\
-  Capacity: int\
-  Photos: String (url to photos)\
-  Description: String\
-  Type: String (Project room, lecture hall)\

Building(id, name, roomCount, address, availableBikes, maxBikes)\
-  Id: int\
-  Name: String\
-  RoomCount: int\
-  Address: String\
-  AvailableBikes: int\
-  MaxBikes: int\

BikeReservation(id, building, numBikes, date, startingTime, endingTime)\
-  Id: int\
-  Building: int\
-  NumBikes: int\
-  Date: Date (String ('yyyy-mm-dd'))\
-  StartingTime: String 'hh:mm:ss'\
-  EndingTime: String 'hh:mm:ss'\

CalenderItems(id, user, title, date, startingTime, endingTime, description)\
-  Id: int\
-  User: String\
-  Title: String\
-  Date: Date (String ('yyyy-mm-dd'))\
-  StartingTime: String 'hh:mm:ss'\
-  EndingTime: String 'hh:mm:ss'\
-  Description: String

Food(id, name, price)\
-  Id: int\
-  Name: String\
-  Price: float\

FoodBuilding(building, food)\
-  Building: int (foreign key building id)\
-  Food: int (foreign key food id)\

FoodReservations(reservation, food, quantity)\
-  Reservation: int (foreign key reservation id)\
-  Food: int (foreign key food id)\
-  Quantity: int