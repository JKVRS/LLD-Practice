package org.lld.parkinglot;

import java.time.LocalDateTime;
import java.util.*;

class Level{
    private  int id;
   private List<ParkingSpot> spots;
   private boolean status;
   private int  numberOfSpots ;
    public Level(int id, boolean status, int numberOfSpots) {
        this.id = id;
        this.spots = new ArrayList<>();
        this.status = status;
        this.numberOfSpots = numberOfSpots;
        for(int i=0; i<numberOfSpots;i++){
            VehicleType type;
            if(i%numberOfSpots==0){
                type = new VehicleType("CAR");
            }else if(i%numberOfSpots ==1) {
                type = new VehicleType("BIKE");
            }else {
                type = new VehicleType("TRUCK");
            }
            spots.add(new ParkingSpot(i+1,false,null, type));
        }
    }

    public int getId() {
        return id;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public boolean isStatus() {
        return status;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }
}

class EntryPoint{
    private int entryPointId;
    private boolean isActive ;

    public EntryPoint(int id) {
        this.entryPointId = id;
        this.isActive = true;
    }

    public int getId() {
        return entryPointId;
    }
}

class ExistPoint {
    private int existPointId;
    private boolean isActive ;

    public ExistPoint(int id) {
        this.existPointId = id;
        this.isActive = true;
    }

    public int getId() {
        return existPointId;
    }
}

class VehicleType{
    public static final VehicleType CAR = new VehicleType("CAR");
    public static final VehicleType BIKE = new VehicleType("BIKE");
    public static final VehicleType TRUCK = new VehicleType("TRUCK");
     String typeName;

     public VehicleType(String typeName){
         this.typeName = typeName;
     }
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VehicleType that = (VehicleType) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }



}


class Vehicle {
    int id;
    VehicleType type;
    String licensePlate;

    public Vehicle(int id,
                   VehicleType type,
                   String licensePlate){
        this.id = id;
        this.type = type;
        this.licensePlate = licensePlate;
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}


class Car extends Vehicle {
    private static final VehicleType carType = new VehicleType("CAR");

    public Car(int id, String licensePlate){
        super(id, carType, licensePlate);
    }
}
class Bike extends Vehicle {
    private static final VehicleType bikeType = new VehicleType("BIKE");

    public Bike(int id, String licensePlate){
        super(id, bikeType, licensePlate);
    }
}
class Truck extends Vehicle {
    private static final VehicleType TruckType = new VehicleType("TRUCK");

    public Truck(int id, String licensePlate){
        super(id, TruckType, licensePlate);
    }
}


class ParkingSpot {
    private int id;
    private boolean isOccupied;
    private Vehicle parkedVehicle;
    private VehicleType type;

    public ParkingSpot(int id, boolean isOccupied, Vehicle parkedVehicle, VehicleType type) {
        this.id = id;
        this.type = type;
        this.isOccupied = isOccupied;
        this.parkedVehicle = parkedVehicle;
    }

    public  int getSpotId(){
        return id;
    }
    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public VehicleType getType() {
        return type;
    }

    public boolean parkVehicle(Vehicle vehicle){
        if(!isOccupied && vehicle.getType().equals(type)){
            this.parkedVehicle = vehicle;
            isOccupied = true;
            return true;
        }
        return  false;
    }

   public void removeVehicle(){
        this.parkedVehicle = null;
        isOccupied = false;
   }
}

interface ParkingStrategy {

    ParkingSpot findParkingSpot(Vehicle vehicle, List<Level> levels);
}
class NearestParkingStrategy implements ParkingStrategy{

    @Override
    public ParkingSpot findParkingSpot(Vehicle vehicle, List<Level> levels) {
          for(Level l : levels){
              for(ParkingSpot spot:l.getSpots()){
                  if(!spot.isOccupied() && vehicle.getType().equals(spot.getType())){
                      return spot;
                  }
              }

          }
          return null;
    }
}

class Ticket {
    private  String id;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private LocalDateTime entryTime;
    private LocalDateTime existTime;



    public Ticket(String id, Vehicle vehicle, ParkingSpot spot) {
        this.id = id;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();


    }

    public String getId(){
        return  id;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExistTime() {
        return existTime;
    }

    public void setExistTime(){
        this.existTime = LocalDateTime.now();
    }

//    public Level getLevel() {
//        return level;
//    }
//
//    public double getPrice() {
//        return price;
//    }

    public  double calculatePrice(PricingStrategy pricingStrategy){
      return  pricingStrategy.calculatePrice(this);
    }
}

interface PricingStrategy{
    double calculatePrice(Ticket ticket);
}
class FlatRatePricing implements PricingStrategy {

    Map<String, Double> rate ;
    // ASSUME 1 hour flat price
    public FlatRatePricing (){
        rate = new HashMap<>();
        rate.put("CAR", 100.00);
        rate.put("TRUCK", 200.00);
        rate.put("BIKE", 50.00);
    }
    @Override
    public double calculatePrice(Ticket ticket) {
       return rate.getOrDefault(ticket.getVehicle().getType().getTypeName(),0.00);
    }
}
class MinuteRatePricing implements PricingStrategy{
    private Double ratePerHour;

    public MinuteRatePricing(Double ratePerHour){
        this.ratePerHour = ratePerHour;
   }


    @Override
    public double calculatePrice(Ticket ticket) {
        double hour = (ticket.getExistTime().getHour() - ticket.getEntryTime().getHour());
        return ratePerHour*hour;

    }
}


class ParkingLot {
      private static volatile ParkingLot instance ;
     private List<Level> level ;
     private String name;
     private String address;
     private List<EntryPoint> entryPoints  ;
    private List<ExistPoint> existPoints;
    private ParkingStrategy parkingStrategy;
    private PricingStrategy pricingStrategy ;
    private Map<String,Ticket> activeTicket;
    private ParkingLot(String name,
                       String address,
                       int entryNumber,
                       int existNumber,
                       int numberOfLevel,
                       int spotsPerLevel,
                       PricingStrategy pricingStrategy){
        this.name = name;
        this.address = address;
        this.level = new ArrayList<>();
         this.entryPoints  = new ArrayList<>() ;
        this.existPoints = new ArrayList<>();
         this.parkingStrategy = new NearestParkingStrategy();
         this.pricingStrategy = pricingStrategy;
         this.activeTicket = new HashMap<>();
        for(int i=0;i<entryNumber;i++){
            entryPoints.add(new EntryPoint(i+1));

        }
        for(int i=0;i<existNumber;i++){
            existPoints.add(new ExistPoint(i+1));

        }
        for(int i=0;i<numberOfLevel;i++){
            level.add(new Level(i+1,false,spotsPerLevel));
        }

    }

    public Ticket parkVehicle(Vehicle vehicle){
       ParkingSpot spot =  parkingStrategy.findParkingSpot(vehicle,level);
       if(spot==null){
           System.out.println("No Available spot for vehicle" + vehicle.getLicensePlate());
           return  null;
       }
        if(spot.parkVehicle(vehicle)){
           String ticketId =  UUID.randomUUID().toString();

           Ticket ticket = new Ticket(ticketId,vehicle,spot);
            System.out.println("Vehicle Parked :"+ vehicle.getLicensePlate() +" - "+spot.getSpotId());
            activeTicket.put(ticketId,ticket);
            return ticket;
        }
        return  null;
    }

    public void removeVehicle(String ticketId){
        Ticket ticket = activeTicket.get(ticketId);
        if(ticket!=null){
            ticket.setExistTime();
           double price =  ticket.calculatePrice(pricingStrategy);
            ticket.getSpot().removeVehicle();
            activeTicket.remove(ticketId);
            System.out.println("Vehicle Parked :"+ ticket.getVehicle().getLicensePlate());
           // System.out.println();
            System.out.println("Vehicle Removed Parking price :" +"$"+ String.format("%.2f",price));
        }
        else {
            System.out.println("Invalid Ticket Id: ");
        }
    }

    public void displayAvailableSlots(){
                for(Level l :level){
                  int availableSpot =  (int) l.getSpots().stream().filter(spot->!spot.isOccupied()).count();
                    System.out.println("Level "+l.getId() +" :"+" spots available ->" + availableSpot);
                }
    }

    public static ParkingLot getInstance(String name,String address,
                                         int entryNumber,
                                         int existNumber,
                                         int numberOfLevel,
                                         int spotsPerLevel,
                                         PricingStrategy pricingStrategy){
        if(instance == null){
            synchronized(ParkingLot.class){
                if(instance == null){
                    instance = new ParkingLot(name, address,entryNumber,existNumber,
                            numberOfLevel,spotsPerLevel,pricingStrategy);
                }
            }
        }
        return instance;
    }

}




public class ParkingLotMain {
    public static void main(String[] args) throws InterruptedException {
         PricingStrategy strategy = new MinuteRatePricing(100.00);
         ParkingLot parkingLot = ParkingLot.getInstance("MyParkingLot", "Pune",
                 2,2,2,3,strategy);

        Vehicle car =  new Car(1,"RJ034");
        Vehicle bike1 = new Bike(2,"MH089");
        Vehicle truck = new Truck(4,"HR009");

        parkingLot.displayAvailableSlots();

      Ticket t1=  parkingLot.parkVehicle(car);
      Ticket t2 =   parkingLot.parkVehicle(bike1);
      // Ticket t3 = parkingLot.parkVehicle(bike2);
        Ticket t4 = parkingLot.parkVehicle(truck);

        parkingLot.displayAvailableSlots();
        Thread.sleep(10000);
        if(t1!=null){
            parkingLot.removeVehicle(t1.getId());
        }
        if(t2!=null){
            parkingLot.removeVehicle(t2.getId());
        }
        parkingLot.displayAvailableSlots();

    }
}
