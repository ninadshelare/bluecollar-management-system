//package com.bluecollar.management.controller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.*;
//
//import com.bluecollar.management.entity.Worker;
//import com.bluecollar.management.entity.enums.PricingType;
//import com.bluecollar.management.service.WorkerService;
//
//@RestController
//@RequestMapping("/api/workers")
//public class WorkerController {
//
//    private final WorkerService workerService;
//
//    public WorkerController(WorkerService workerService) {
//        this.workerService = workerService;
//    }
//
//    @GetMapping("/search")
//    public List<Worker> searchWorkers(
//            @RequestParam String service,
//            @RequestParam PricingType pricingType,
//            @RequestParam Double maxPrice,
//            @RequestParam Boolean available,
//            @RequestParam Double minRating
//    ) {
//        return workerService.searchWorkers(
//                service,
//                pricingType,
//                maxPrice,
//                available,
//                minRating
//        );
//    }
//}
