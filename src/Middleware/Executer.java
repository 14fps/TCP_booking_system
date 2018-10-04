package Middleware;

import Server.TCP.TCPController;
import Server.Utli.Command;

import java.util.Vector;

import static Server.Utli.Executer.toBoolean;


/**
 * Created by lidawei on 07/04/2017.
 */
public class Executer {

    public static String execute(Command cmd, Vector<String> arguments,String command)
    {
        switch (cmd)
        {
            case AddFlight: {
                return TCPMiddleware.flightExecute(command);
            }
            case AddCars: {
                return TCPMiddleware.carExecute(command);
            }
            case AddRooms: {
                return TCPMiddleware.roomExecute(command);
            }
            case AddCustomerID: {
                TCPMiddleware.carExecute(command);
                TCPMiddleware.roomExecute(command);
                TCPMiddleware.flightExecute(command);
                return TCPMiddleware.customerExecute(command);
            }
            case DeleteFlight: {
                return TCPMiddleware.flightExecute(command);
            }
            case DeleteCars: {
                return TCPMiddleware.carExecute(command);
            }
            case DeleteRooms: {
                return TCPMiddleware.roomExecute(command);
            }
            case DeleteCustomer: {
                TCPMiddleware.flightExecute(command);
                TCPMiddleware.roomExecute(command);
                TCPMiddleware.carExecute(command);
                return TCPMiddleware.customerExecute(command);
            }
            case QueryFlight: {
                return TCPMiddleware.flightExecute(command);
            }
            case QueryCars: {
                return TCPMiddleware.carExecute(command);
            }
            case QueryRooms: {
                return TCPMiddleware.roomExecute(command);
            }
            case QueryCustomer: {
                return TCPMiddleware.customerExecute(command);
            }
            case QueryFlightPrice: {
                return TCPMiddleware.flightExecute(command);
            }
            case QueryCarsPrice: {
                return TCPMiddleware.carExecute(command);
            }
            case QueryRoomsPrice: {
                return TCPMiddleware.roomExecute(command);
            }
            case ReserveFlight: {
                return TCPMiddleware.flightExecute(command);
            }
            case ReserveCar: {
                return TCPMiddleware.carExecute(command);
            }
            case ReserveRoom: {
                return TCPMiddleware.roomExecute(command);
            }
            case Bundle: {

                String id = arguments.elementAt(1);
                String customerID = arguments.elementAt(2);
                Vector<String> flightNumbers = new Vector<String>();
                for (int i = 0; i < arguments.size() - 6; ++i)
                {
                    flightNumbers.addElement(arguments.elementAt(3+i));
                }
                String location = arguments.elementAt(arguments.size()-3);
                String car = arguments.elementAt(arguments.size()-2);
                String room = arguments.elementAt(arguments.size()-1);

                for(String flightNumber: flightNumbers){
                    if(Integer.parseInt(TCPMiddleware.flightExecute(makeQueryFlightCmd.
                            buildCMD(id, customerID, flightNumber))) <= 0)
                        return "false";
                }
                if(toBoolean(car)){
                    if(Integer.parseInt(TCPMiddleware.carExecute(makeQueryCarCmd.
                            buildCMD(id, customerID, location))) <= 0)
                        return "false";
                }
                if(toBoolean(room)){
                    if(Integer.parseInt(TCPMiddleware.roomExecute(makeQueryRoomCmd.
                            buildCMD(id, customerID, location))) <= 0)
                        return "false";
                }
                Boolean flag = true;
                for(String flightNumber: flightNumbers){
                    if(!toBoolean(TCPMiddleware.flightExecute(makeReserveFlightCmd.
                            buildCMD(id,customerID,flightNumber))))
                        flag = false;
                }
                if(toBoolean(car)){
                    if(!toBoolean(TCPMiddleware.carExecute(makeReserveCarCmd.
                            buildCMD(id, customerID,location))))
                        flag = false;
                }
                if(toBoolean(room)){
                    if(!toBoolean(TCPMiddleware.roomExecute(makeReserveRoomCmd.
                            buildCMD(id, customerID,location))))
                        flag = false;
                }
                if(flag) return "true";
                return "false";
            }
        }
        return "unknow cmd";
    }

    static CmdBuilder makeReserveFlightCmd = (id,customerID,flightNumber) -> "ReserveFlight,"+id+","+customerID+","+flightNumber;
    static CmdBuilder makeReserveCarCmd = (id,customerID,location) -> "ReserveCar,"+id+","+customerID+","+location;
    static CmdBuilder makeReserveRoomCmd = (id,customerID,location) -> "ReserveRoom,"+id+","+customerID+","+location;
    static CmdBuilder makeQueryFlightCmd = (id,customerID,flightNumber) -> "QueryFlight,"+id+","+flightNumber;
    static CmdBuilder makeQueryCarCmd = (id,customer,location) -> "QueryCars,"+id+","+location;
    static CmdBuilder makeQueryRoomCmd = (id,customer,location) -> "QueryRooms,"+id+","+location;

}

