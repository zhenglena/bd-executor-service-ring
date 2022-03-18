package com.amazon.ata.executorservice.test;

import com.amazon.ata.executorservice.checker.DeviceCheckTask;
import com.amazon.ata.executorservice.checker.DeviceChecker;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.GetDeviceSystemInfoRequest;
import com.amazon.ata.executorservice.coralgenerated.devicecommunication.RingDeviceFirmwareVersion;
import com.amazon.ata.executorservice.customer.CustomerService;
import com.amazon.ata.executorservice.devicecommunication.RingDeviceCommunicatorService;
import com.amazon.ata.executorservice.util.KnownRingDeviceFirmwareVersions;
import com.amazon.ata.executorservice.wrapper.DeviceCheckTaskWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class Phase1Test {
    @Test
    void deviceCheckTask_implementsFunctionalInterface() {
        // GIVEN
        String[] methods = {"run"};
        DeviceCheckTask task = buildDeviceCheckTask();
        // WHEN

        // THEN
        assertTrue(Runnable.class.isInstance(task));
        try {
            for (String method : methods) {
                DeviceCheckTask.class.getMethod(method);
            }
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
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

    private int buildDeviceCheckAttempt = 0;

    private DeviceCheckTask buildDeviceCheckTask() {
        DeviceCheckTask task = null;
        try {
            if (buildDeviceCheckAttempt == 0) {
                task = (DeviceCheckTask) DeviceCheckTask.class.getConstructors()[0].newInstance(new DeviceChecker(null, null), null, null);
            } else {
                task = (DeviceCheckTask) DeviceCheckTask.class.getConstructors()[0].newInstance(new DeviceChecker(null, null));
            }
        } catch (Exception e) {
            buildDeviceCheckAttempt++;
            task = buildDeviceCheckTask();
        }
        assertNotNull(task);
        return task;
    }
}
