package com.amazon.ata.executorservice.util;

import com.amazon.ata.executorservice.coralgenerated.devicecommunication.RingDeviceFirmwareVersion;

import java.util.Comparator;

/**
 * Compares RingDeviceFirmwareVersion objects.
 */
public class RingDeviceFirmwareVersionComparator implements Comparator<RingDeviceFirmwareVersion> {

    @Override
    public int compare(RingDeviceFirmwareVersion leftVersion, RingDeviceFirmwareVersion rightVersion) {
        String[] leftVersionSegments = leftVersion.getVersionNumber().split("\\.");
        String[] rightVersionSegments = rightVersion.getVersionNumber().split("\\.");
        int minLength = Math.min(leftVersionSegments.length, rightVersionSegments.length);

        for (int i = 0; i < minLength; i++) {
            int leftVersionSegmentValue = Integer.parseInt(leftVersionSegments[i]);
            int rightVersionSegmentValue = Integer.parseInt(rightVersionSegments[i]);

            if (leftVersionSegmentValue != rightVersionSegmentValue) {
                return Integer.compare(leftVersionSegmentValue, rightVersionSegmentValue);
            }
        }

        return Integer.compare(leftVersionSegments.length, rightVersionSegments.length);
    }
}
