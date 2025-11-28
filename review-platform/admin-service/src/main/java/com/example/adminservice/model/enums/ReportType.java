package com.example.adminservice.model.enums;

// Đổi tên các giá trị trong SQL (ví dụ: 'HateSpeech')
// thành tên hằng số Java (ví dụ: 'HATE_SPEECH')
// JPA sẽ tự động map chúng nếu dùng @Enumerated(EnumType.STRING)
public enum ReportType {
    Harassment,
    HateSpeech,
    SexualContent,
    Violence,
    Misinformation,
    Spam,
    Impersonation,
    SelfHarm,
    ChildEndangerment,
    PrivacyViolation,
    Terrorism,
    Other
}