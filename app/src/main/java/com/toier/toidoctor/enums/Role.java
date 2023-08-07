package com.toier.toidoctor.enums;

import androidx.annotation.NonNull;

public enum Role {
    PATIENT {
        @Override
        public String toString() {
            return "patient";
        }
    },
    DOCTOR {
        @Override
        public String toString() {
            return "doctor";
        }
    };

    public static Role getRole(String s) {
        return (s.equals(PATIENT.toString()) ? PATIENT : DOCTOR);
    }
}
