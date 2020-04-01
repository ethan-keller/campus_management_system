package nl.tudelft.oopp.demo.logic;

import java.io.UnsupportedEncodingException;

import javafx.scene.control.TableView;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.communication.FoodServerCommunication;
import nl.tudelft.oopp.demo.communication.ReservationServerCommunication;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.UserServerCommunication;
import nl.tudelft.oopp.demo.controllers.AdminManageFoodViewController;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodReservation;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;

public class AdminLogic {

    /**.
     * This method is used to select a building from the tabular view of the buildings
     * Constraints are added; if the building index is less than 0, null is returned.
     * @param buildingTableView - TableView of Buildings
     * @return - Building
     */
    public static Building getSelectedBuildingLogic(TableView<Building> buildingTableView) {
        if (buildingTableView.getSelectionModel().getSelectedIndex() >= 0) {
            return buildingTableView.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used in the adminManageBuildingViewController class to communicate with the server to
     * command them to delete the selected building.
     * @param selectedBuilding - The selected building from the tabular view passed as a parameter.
     */
    public static void deleteBuildingLogic(Building selectedBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.deleteBuilding(selectedBuilding.getBuildingId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageBuildingViewController class to communicate with the server to
     * command them to create a new building.
     * @param tempBuilding - The features of the building are passed through this variable.
     */
    public static void createBuildingLogic(Building tempBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.createBuilding(tempBuilding.getBuildingName().get(),
                    tempBuilding.getBuildingRoomCount().get(), tempBuilding.getBuildingAddress().get(),
                    tempBuilding.getOpeningTime().get(), tempBuilding.getClosingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used in the adminManageBuildingViewController class to communicate with the server to
     * command them to edit the selected building.
     * @param selectedBuilding - The selected building from the tabular view passed as a parameter.
     * @param tempBuilding - The features of the building are passed through this variable.
     */
    public static void editBuildingLogic(Building selectedBuilding, Building tempBuilding) {
        try {
            // Communication with the server.
            BuildingServerCommunication.updateBuilding(selectedBuilding.getBuildingId().get(),
                    tempBuilding.getBuildingName().get(), tempBuilding.getBuildingRoomCount().get(),
                    tempBuilding.getBuildingAddress().get(), tempBuilding.getBuildingMaxBikes().get(),
                    tempBuilding.getOpeningTime().get(), tempBuilding.getClosingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used in adminManageFoodReservationController class to communicate with the server to
     * command them to delete a food reservation for a particular building.
     * @param selectedBuilding - The building where the food is provided
     */
    public static void deleteFoodReservationLogic(Building selectedBuilding) {
        try {
            FoodServerCommunication.deleteFoodFromBuilding(
                    AdminManageFoodViewController.currentSelectedFood.getFoodId().get(),
                    selectedBuilding.getBuildingId().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used in adminManageFoodReservationController class to communicate with the server to
     * command them to add a food reservation for a particular building.
     * @param tempBuilding - The building variable with all the food options
     */
    public static void addFoodReservationLogic(Building tempBuilding) {
        try {
            FoodServerCommunication.addFoodToBuilding(
                    AdminManageFoodViewController.currentSelectedFood.getFoodId().get(),
                    tempBuilding.getBuildingId().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods returns the selected food reservation from the food reservation table.
     * @param foodReservationTable - The table of food reservations.
     * @return - FoodReservation corresponding to the one the user selected.
     */
    public static FoodReservation getSelectedFoodReservation(TableView<FoodReservation> foodReservationTable) {
        if (foodReservationTable.getSelectionModel().getSelectedIndex() >= 0) {
            return foodReservationTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used to select a reservation from the tabular view of the reservations
     * Constraints are added; if the reservation index is less than 0, null is returned.
     * @param reservationTableView - TableView of Reservations
     * @return - Reservation
     */
    public static Reservation getSelectedReservation(TableView<Reservation> reservationTableView) {
        // If reservation selection is valid:
        if (reservationTableView.getSelectionModel().getSelectedIndex() >= 0) {
            // Returns the item ( of type Reservation ) back to the user.
            return reservationTableView.getSelectionModel().getSelectedItem();
        } else {
            // If no item is selected, then null is returned.
            return null;
        }
    }

    /**.
     * This method is used in the adminManageReservationViewController class to communicate with the server to
     * command them to delete the selected reservation.
     * @param selectedReservation - The selected reservation from the tabular view passed as a parameter.
     */
    public static void deleteReservationLogic(Reservation selectedReservation) {
        try {
            ReservationServerCommunication.deleteReservation(selectedReservation.getId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageReservationViewController class to communicate with the server to
     *  command them to create a new reservation.
     * @param tempReservation - The features of the new reservation passed as parameter.
     */
    public static void createReservationLogic(Reservation tempReservation) {
        try {
            ReservationServerCommunication.createReservation(tempReservation.getUsername().get(),
                    tempReservation.getRoom().get(), tempReservation.getDate().get(),
                    tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in the adminManageReservationViewController class to communicate with the server to
     * command them to edit the selected reservation.
     * @param selectedReservation - This is used to get the id of the selected reservation.
     * @param tempReservation - These are the edited features of the reservation object passed as parameter.
     */
    public static void editReservationLogic(Reservation selectedReservation, Reservation tempReservation) {
        try {
            ReservationServerCommunication.updateReservation(selectedReservation.getId().get(),
                    tempReservation.getRoom().get(), tempReservation.getDate().get(),
                    tempReservation.getStartingTime().get(), tempReservation.getEndingTime().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used to select a food from the tabular view of the rooms
     * Constraints are added; if the food index is less than 0, null is returned.
     * @param foodTable - The selected food from the table
     * @return - Food
     */
    public static Food getSelectedFood(TableView<Food> foodTable) {
        if (foodTable.getSelectionModel().getSelectedIndex() >= 0) {
            return foodTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**
     * Commands the server to deleted the selected food.
     * @param selectedFood - Food option selected by the user.
     */
    public static void deleteFoodLogic(Food selectedFood) {
        try {
            FoodServerCommunication.deleteFood(selectedFood.getFoodId().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Commands the server to add food to the food options.
     * @param tempFood - Food option selected by the user.
     */
    public static void addFoodLogic(Food tempFood) {
        try {
            FoodServerCommunication.createFood(tempFood.getFoodName().get(), tempFood.getFoodPrice().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Commands the server to update food to the food options.
     * @param selectedFood - Food option selected by the user.
     * @param tempFood - Food option updated by the user.
     */
    public static void updateFoodLogic(Food selectedFood, Food tempFood) {
        try {
            FoodServerCommunication.updateFood(selectedFood.getFoodId().get(),
                    tempFood.getFoodName().get(), tempFood.getFoodPrice().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used to select a room from the tabular view of the rooms
     * Constraints are added; if the room index is less than 0, null is returned.
     * @param roomTable - The selected room from the table
     * @return - Room
     */
    public static Room getSelectedRoom(TableView<Room> roomTable) {
        if (roomTable.getSelectionModel().getSelectedIndex() >= 0) {
            return roomTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used in the adminManageRoomViewController class to communicate with the server to
     * command them to delete the selected room.
     * @param selectedRoom - Selected room from the table
     */
    public static void deleteRoomLogic(Room selectedRoom) {
        try {
            RoomServerCommunication.deleteRoom(selectedRoom.getRoomId().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageRoomViewController class to communicate with the server to
     * command them to create a new room.
     * @param tempRoom - A room with all the required features to be created.
     */
    public static boolean createRoomLogic(Room tempRoom) {
        try {
            if (RoomServerCommunication.createRoom(tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                    tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get())) {
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**.
     * This method is used in the adminManageRoomViewController class to communicate with the server to
     * command them to edit the selected room.
     * @param selectedRoom - This is used to get the id of the selected room.
     * @param tempRoom - These are the edited features of the room object passed as parameter.
     */
    public static boolean editRoomLogic(Room selectedRoom, Room tempRoom) {
        try {
            if (RoomServerCommunication.updateRoom(selectedRoom.getRoomId().get(),
                    tempRoom.getRoomName().get(), tempRoom.getRoomBuilding().get(),
                    tempRoom.getTeacherOnly().get(), tempRoom.getRoomCapacity().get(),
                    tempRoom.getRoomPhoto().get(), tempRoom.getRoomDescription().get(),
                    tempRoom.getRoomType().get())) {
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**.
     * This method is used to select a User from the tabular view of the Users
     * Constraints are added; if the User index is less than 0, null is returned.
     * @param userTable - The selected User from the table
     * @return - User
     */
    public static User getSelectedUser(TableView<User> userTable) {
        if (userTable.getSelectionModel().getSelectedIndex() >= 0) {
            return userTable.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    /**.
     * This method is used in the adminManageUserViewController class to communicate with the server to
     * command them to delete the selected User.
     * @param selectedUser - Selected User from the table
     */
    public static void deleteUserLogic(User selectedUser) {
        try {
            UserServerCommunication.deleteUser(selectedUser.getUsername().getValue());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**.
     * This method is used in adminManageUserViewController class to communicate with the server to
     * command them to create a new User.
     * @param tempUser - A User with all the required features to be created.
     */
    public static void createUserLogic(User tempUser) {
        try {
            UserServerCommunication.createUser(tempUser.getUsername().get(), tempUser.getUserPassword().get(),
                    tempUser.getUserType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();;
        }
    }

    /**.
     * This method is used in the adminManageUserViewController class to communicate with the server to
     * command them to edit the selected User.
     * @param tempUser - These are the edited features of the User object passed as parameter.
     */
    public static void editUserLogic(User tempUser) {
        try {
            UserServerCommunication.updateUser(tempUser.getUsername().get(),
                    tempUser.getUserPassword().get(), tempUser.getUserType().get());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
