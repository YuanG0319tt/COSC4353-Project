// package com.example.volunteerMatching.services;

// import com.example.volunteerMatching.models.VolHistory;
// import com.example.volunteerMatching.models.VolunteerHistory;
// import com.example.volunteerMatching.repositories.VolunteerHistoryRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class VolHistoryService {
//     private final VolunteerHistoryRepository volunteerHistoryRepository;

//     public VolHistoryService(VolunteerHistoryRepository volunteerHistoryRepository) {
//         this.volunteerHistoryRepository = volunteerHistoryRepository;
//     }

//     // Add new volunteer history record
//     public VolHistory addVolHistory(VolHistory volHistoryDto) {
//         // Convert DTO to entity
//         VolunteerHistory entity = convertToEntity(volHistoryDto);
        
//         // Save the entity
//         VolunteerHistory saved = volunteerHistoryRepository.save(entity);
        
//         // Convert back to DTO for the view layer
//         return convertToDto(saved);
//     }

//     // Get all volunteer history records
//     public List<VolHistory> getAllVolHistory() {
//         List<VolunteerHistory> histories = volunteerHistoryRepository.findAllWithUserAndEventDetails();
//         System.out.println("Volunteer History List: " + histories);
//         return histories.stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }

//     // Get volunteer history by ID
//     public Optional<VolHistory> getVolHistoryById(Long id) {
//         return volunteerHistoryRepository.findById(id)
//                 .map(this::convertToDto);
//     }

//     // Delete volunteer history
//     public void deleteVolHistory(Long id) {
//         volunteerHistoryRepository.deleteById(id);
//     }

//     // Helper method to convert entity to DTO
//     private VolHistory convertToDto(VolunteerHistory entity) {
//         VolHistory dto = new VolHistory();
        
//         // Set ID from the entity
//         dto.setId(entity.getHistoryId());
        
//         // Set user information from UserInfo
//         if (entity.getUserCredentials() != null && entity.getUserCredentials().getUserInfo() != null) {
//             dto.setName(entity.getUserCredentials().getUserInfo().getName());
//             dto.setEmail(entity.getUserCredentials().getUserInfo().getEmail());
//             dto.setPhoneNumber(entity.getUserCredentials().getUserInfo().getPhoneNumber());
//         }
        
//         // Set event information from EventDetails
//         if (entity.getEventDetails() != null) {
//             dto.setEventName(entity.getEventDetails().getEventName());
//             if (entity.getEventDetails().getEventDate() != null) {
//                 dto.setEventDate(entity.getEventDetails().getEventDate().toString());
//             }
//             // You'll need to determine how to set hours volunteered
//             // This might come from a different field in your database
//             dto.setHoursVolunteered(0); // Default value, replace with actual hours
//         }
        
//         // Set status - you might need to determine this based on your business logic
//         dto.setStatus("Completed"); // Default value, replace with actual status
        
//         return dto;
//     }

//     // Helper method to convert DTO to entity
//     // This would need additional service injections to look up the related entities
//     private VolunteerHistory convertToEntity(VolHistory dto) {
//         // This is a simplified version - you'll need to fetch the related entities
//         // from their respective repositories
//         VolunteerHistory entity = new VolunteerHistory();
        
//         // If updating an existing record
//         if (dto.getId() != null) {
//             entity.setHistoryId(dto.getId());
//         }
        
//         // You'll need to inject these services and use them to find entities
//         // UserCredentialsService userCredentialsService;
//         // EventDetailsService eventDetailsService;
        
//         // Find the user by email (or other identifier)
//         // entity.setUserCredentials(userCredentialsService.findByEmail(dto.getEmail()));
        
//         // Find the event by name (or other identifier)
//         // entity.setEventDetails(eventDetailsService.findByName(dto.getEventName()));
        
//         // Set the participation date
//         // entity.setParticipationDate(LocalDate.parse(dto.getEventDate()));
        
//         return entity;
//     }
// }
