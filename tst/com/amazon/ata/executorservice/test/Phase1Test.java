package com.amazon.ata.executorservice.classroom.test;

import com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication.GetDeviceSystemInfoRequest;
import com.amazon.ata.executorservice.classroom.coralgenerated.devicecommunication.RingDeviceFirmwareVersion;
import com.amazon.ata.executorservice.classroom.test.wrapper.DeviceCheckTaskWrapper;
import com.amazon.ata.executorservice.classroom.checker.DeviceCheckTask;
import com.amazon.ata.executorservice.classroom.checker.DeviceChecker;
import com.amazon.ata.executorservice.classroom.customer.CustomerService;
import com.amazon.ata.executorservice.classroom.devicecommunication.RingDeviceCommunicatorService;
import com.amazon.ata.executorservice.classroom.util.KnownRingDeviceFirmwareVersions;
import com.amazon.ata.test.assertions.IntrospectionAssertions;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class Phase1Test {
    @Test
    void deviceCheckTask_implementsFunctionalInterface() {
        // GIVEN
        String[] methods = {"run"};
        // WHEN

        // THEN
        IntrospectionAssertions.assertImplementsInterface(DeviceCheckTask.class, Runnable.class);
        IntrospectionAssertions.assertClassContainsMemberMethodNames(DeviceCheckTask.class, methods);
    }

    @Test
    void deviceCheckTask_run_callsGetDeviceSystemInfo() {
        // GIVEN
        // spy on the RingDeviceCommunicatorService to see args passed to it
        RingDeviceCommunicatorService deviceCommunicatorSpy = spy(RingDeviceCommunicatorService.getClient());
        ArgumentCaptor<GetDeviceSystemInfoRequest> requestCaptor =
            ArgumentCaptor.forClass(GetDeviceSystemInfoRequest.class);
        // DeviceChecker in use
        DeviceChecker deviceChecker = new DeviceChecker(CustomerService.getClient(), deviceCommunicatorSpy);
        // device to check
        String deviceId = "1234";
        // version to query for
        RingDeviceFirmwareVersion version = KnownRingDeviceFirmwareVersions.PINKY;
        // the task to run
        DeviceCheckTaskWrapper taskWrapper = new DeviceCheckTaskWrapper(deviceChecker, deviceId, version);

        // WHEN
        taskWrapper.run();

        // THEN
        // the RingDeviceCommunicatorService was called...
        verify(deviceCommunicatorSpy).getDeviceSystemInfo(requestCaptor.capture());
        GetDeviceSystemInfoRequest request = requestCaptor.getValue();
        // ...with expected device ID
        assertEquals(
            deviceId,
            request.getDeviceId(),
            String.format("Expected request to contain device ID '%s', but request was %s.", deviceId, request)
        );
    }

    @Test
    void deviceCheckTask_run_forOutOfDateDevice_callsUpdateDevice() {
        // GIVEN
        // spy on the DeviceChecker instance to see args passed to updateDevice()
        DeviceChecker deviceCheckerSpy =
            spy(new DeviceChecker(CustomerService.getClient(), RingDeviceCommunicatorService.getClient()));
        // device to check
        String deviceId = "1234";
        // the version to check for, which should always trigger an update
        RingDeviceFirmwareVersion bleedingEdgeVersion = RingDeviceFirmwareVersion.builder()
                .withVersionNumber("100.0")
                .build();
        // the task to run
        DeviceCheckTaskWrapper taskWrapper =
                new DeviceCheckTaskWrapper(deviceCheckerSpy, deviceId, bleedingEdgeVersion);

        // WHEN
        taskWrapper.run();

        // THEN
        verify(deviceCheckerSpy).updateDevice(deviceId, bleedingEdgeVersion);
    }
}
