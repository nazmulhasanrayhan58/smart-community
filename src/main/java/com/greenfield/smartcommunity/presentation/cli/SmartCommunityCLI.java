package com.greenfield.smartcommunity.presentation.cli;

import com.greenfield.smartcommunity.domain.enums.RequestStatus;
import com.greenfield.smartcommunity.domain.request.GISLocation;
import com.greenfield.smartcommunity.domain.request.ServiceRequest;
import com.greenfield.smartcommunity.integration.adapters.ConsoleNotificationAdapter;
import com.greenfield.smartcommunity.integration.adapters.MockGISAdapter;
import com.greenfield.smartcommunity.persistence.impl.InMemoryRequestRepository;
import com.greenfield.smartcommunity.service.ServiceRequestService;

import java.util.List;
import java.util.Scanner;

public class SmartCommunityCLI {

    private static final ServiceRequestService service = new ServiceRequestService(
            new InMemoryRequestRepository(),
            new ConsoleNotificationAdapter(),
            new MockGISAdapter()
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> submitRequest(scanner);
                    case "2" -> viewAllRequests();
                    case "3" -> updateStatus(scanner);
                    case "4" -> assignRequest(scanner);
                    case "0" -> { System.out.println("Exiting."); return; }
                    default  -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Smart Community Services Platform ===");
        System.out.println("1. Submit a new service request");
        System.out.println("2. View all requests");
        System.out.println("3. Update request status");
        System.out.println("4. Assign request to maintenance team");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private static void submitRequest(Scanner scanner) {
        System.out.print("Category (ROAD_DAMAGE/WASTE_COLLECTION/GRAFFITI/STREET_LIGHTING/OTHER): ");
        String category = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        GISLocation location = new GISLocation(-37.8136, 144.9631, address);
        ServiceRequest request = service.createRequest(category, description, location);
        System.out.println("Created: " + request);
    }

    private static void viewAllRequests() {
        List<ServiceRequest> requests = service.getAllRequests();
        if (requests.isEmpty()) {
            System.out.println("No requests yet.");
            return;
        }
        requests.forEach(System.out::println);
    }

    private static void updateStatus(Scanner scanner) {
        System.out.print("Request ID: ");
        String id = scanner.nextLine();
        System.out.print("New status (IN_REVIEW/ASSIGNED/IN_PROGRESS/RESOLVED/CLOSED): ");
        String status = scanner.nextLine();
        service.updateStatus(id, RequestStatus.valueOf(status), "resident@example.com");
        System.out.println("Status updated.");
    }

    private static void assignRequest(Scanner scanner) {
        System.out.print("Request ID: ");
        String id = scanner.nextLine();
        System.out.print("Team ID: ");
        String teamId = scanner.nextLine();
        service.assignToTeam(id, teamId);
        System.out.println("Assigned to team " + teamId);
    }
}