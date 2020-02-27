package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    public User() {
    }


    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return this.username.equals(user.username);
    }

}
    @Entity
    @Table(name = "reservation")
    public class Reservation {
        @Id
        @Column(name = "id")
        private int id;

        @Column(name = "username")
        private String username;

        @Column(name = "room")
        private String room;

        @Column(name = "date")
        private Date date;

        @Column(name = "starting_time")
        private int starting_time;

        @Column(name = "ending_time")
        private int ending_time;

        public Reservation() {
        }


        public Reservation(int id, String username, Date date, int starting_time, int ending_time) {
            this.id = id;
            this.username = username;
            this.date = date;
            this.starting_time = starting_time;
            this.ending_time = ending_time;

        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public Date getDate() {
            return date;
        }

        public int getStarting_time() {
            return starting_time;
        }

        public int getEnding_time() {
            return ending_time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Reservation reservation = (Reservation) o;

            return this.getId() == (reservation.getId());
        }

    }

        @Entity
        @Table(name = "room")
        public class Room {
            @Id
            @Column(name = "id")
            private int id;

            @Column(name = "username")
            private String username;

            @Column(name = "building")
            private int building;

            @Column(name = "teacher_only")
            private boolean teacher_only;

            @Column(name = "capacity")
            private int capacity;

            @Column(name = "photos")
            private String photos;

            @Column(name = "description")
            private String description;

            public Room() {
            }


            public Room(int id, String username, int building, Boolean teacher_only, int capacity, String photos, String description) {
                this.id = id;
                this.username = username;
                this.building = building;
                this.teacher_only = teacher_only;
                this.capacity = capacity;
                this.photos = photos;
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }

            public int getBuilding(){
                return building;
            }

            public boolean isTeacher_only() {
                return teacher_only;
            }

            public int getCapacity(){
                return capacity;
            }

            public String getPhotos(){
                return photos;
            }

            public String getDescription(){
                return description;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }

                Room room = (Room) o;

                return this.id == room.getId();
            }
        }

@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_count")
    private int room_count;

    @Column(name = "address")
    private String address;

    public Building() {
    }


    public Building(int id, String name, int room_count, String address) {
        this.id = id;
        this.name = name;
        this.room_count = room_count;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRoom_count() {
        return room_count;
    }

    public int getRoom_count() {
        return room_count;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Building building = (Building) o;

        return this.id == building.id;
    }
}

