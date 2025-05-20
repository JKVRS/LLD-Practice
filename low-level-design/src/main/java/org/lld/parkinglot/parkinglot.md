1. The parking lot should have multiple levels, each level with a certain number of parking spots.
2. The parking lot should support different types of vehicles, such as cars, motorcycles, and trucks.
3. Each parking spot should be able to accommodate a specific type of vehicle.
4. The system should assign a parking spot to a vehicle upon entry and release it when the vehicle exits.
5. The system should track the availability of parking spots and provide real-time information to customers.
6. The system should handle multiple entry and exit points and support concurrent access.

### Entities/class:   TOP-DOWN                 
* ParkingLot 
* Level
* ParkingSpot
* Vehicle
* EntryPoint
* ExistPoint
* Ticket

### Actors:  Customer, Guard, System

Attributes per class:
1. ParkingLot - List<Level> level, String name, String address, List<Entrypoint> entryPoints, List<ExistPoint> existPoints
2. Level - int id, List<ParkingSpot> spots, boolean status
3. ParkingSpot - int id, VehicleType type, boolean isOccupied, Vehicle parkedVehicle, 
4. abstract Vehicle - int id, String licencePlate, VehicleType type, 
5. VehicleSubclass - CAR, BICYCLE, BIKE, TRUCK,
6. abstract Point - int id, boolean isActive
7. SubClasses(EntryPoint, ExitPoint)
8. Ticket - int id, Vehicle vehicle, ParkingSpot, LocalTimeDate entryTime, LocalTimeDate existTime, Level level, ParkingLot parkingLot, double price

Interface :
1. parkingInterface - ParkingSpot findParkingSpot(Vehicle vehicle , List<Level> level)
   Implementations Classes - nearestBySpot, cheapestSpot

2. paymentInterface - calculateCost(Ticket ticket)
                      processPayment(double amount)
   Implementation classes : OnlinePayment, CashPayment


### DesignPatters :

* ParkingLot - singleton
* Vehicle - Factory
* parkingStrategy - Strategy
* calculatePrice - Strategy

### Mapping & Relationship :

1. ParkingLot <-> Level = 1 : m (Composition) - HAS-A
2. Level <-> ParkingSpot = 1:m (Composition) - HAS-A
3. ParkingSpot -> Vehicle = 1:1 (Association) -Vehicle utilize parking spot
4. ParkingLot <-> entryPoints = 1:m (Composition) - HAS-A
5. ParkingLot <-> existPoints = 1:m (Composition) - HAS-A
6. Vehicle <- CAR = IS-A Generalization



