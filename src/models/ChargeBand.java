package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChargeBand {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal serviceCharge;
    private BigDecimal cleaningCharge;

    public ChargeBand(LocalDate startDate, LocalDate endDate, BigDecimal serviceCharge, BigDecimal cleaningCharge) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.serviceCharge = serviceCharge;
        this.cleaningCharge = cleaningCharge;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public BigDecimal getCleaningCharge() {
        return cleaningCharge;
    }
}
