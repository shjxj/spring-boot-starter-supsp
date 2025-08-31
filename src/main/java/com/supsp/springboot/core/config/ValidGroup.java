package com.supsp.springboot.core.config;

import jakarta.validation.GroupSequence;

public class ValidGroup {
    public interface Insert {
    }

    public interface Update {
    }

    public interface save {
    }

    public interface Delete {
    }

    public interface AccountLogin {
    }

    public interface DeviceLogin {
    }

    public interface MobileCodeLogin {
    }

    public interface QrLogin {
    }


    @GroupSequence({Insert.class, Update.class})
    public interface InsertOrUpdate {
    }

    @GroupSequence({Insert.class, Update.class, Delete.class})
    public interface All {
    }
}
