//package com.bluecollar.management.controller;
//
//import org.springframework.web.bind.annotation.*;
//
//import com.bluecollar.management.entity.Worker;
//import com.bluecollar.management.service.WorkerService;
//
//@RestController
//@RequestMapping("/test/worker")
//public class WorkerTestController {
//
//    private final WorkerService workerService;
//
//    public WorkerTestController(WorkerService workerService) {
//        this.workerService = workerService;
//    }
//
//    @PostMapping("/create")
//    public Worker createWorker(
//            @RequestParam Long userId,
//            @RequestParam String serviceName,
//            @RequestParam Integer experience) {
//
//        return workerService.createWorker(userId, serviceName, experience);
//    }
//}
