package org.geekhub.kovalchuk.json.entity;

import java.util.Map;

public class MonthPriceJsonResponse {
    public enum Unit {
        PRICE_UNIT_WHOLE;
    }

    public enum UpdateStatus {
        PRICE_UPDATE_STATUS_UNSPECIFIED;
    }

    private String status;
    private Content content;

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public Content getContent() { return content; }
    public void setContent(Content value) { this.content = value; }

    public static class Content {
        private Results results;
        private GroupingOptions groupingOptions;

        public Results getResults() {
            return results;
        }

        public void setResults(Results value) {
            this.results = value;
        }

        public GroupingOptions getGroupingOptions() {
            return groupingOptions;
        }

        public void setGroupingOptions(GroupingOptions value) {
            this.groupingOptions = value;
        }

        public static class Results {
            private Map<String, Quote> quotes;
            private Map<String, Carrier> carriers;
            private Map<String, Place> places;

            public Map<String, Quote> getQuotes() {
                return quotes;
            }

            public void setQuotes(Map<String, Quote> value) {
                this.quotes = value;
            }

            public Map<String, Carrier> getCarriers() {
                return carriers;
            }

            public void setCarriers(Map<String, Carrier> value) {
                this.carriers = value;
            }

            public Map<String, Place> getPlaces() {
                return places;
            }

            public void setPlaces(Map<String, Place> value) {
                this.places = value;
            }

            public static class Quote {
                private MinPrice minPrice;
                private boolean isDirect;
                private BoundLeg outboundLeg;
                private BoundLeg inboundLeg;

                public MinPrice getMinPrice() {
                    return minPrice;
                }

                public void setMinPrice(MinPrice value) {
                    this.minPrice = value;
                }

                public boolean getIsDirect() {
                    return isDirect;
                }

                public void setIsDirect(boolean value) {
                    this.isDirect = value;
                }

                public BoundLeg getOutboundLeg() {
                    return outboundLeg;
                }

                public void setOutboundLeg(BoundLeg value) {
                    this.outboundLeg = value;
                }

                public BoundLeg getInboundLeg() {
                    return inboundLeg;
                }

                public void setInboundLeg(BoundLeg value) {
                    this.inboundLeg = value;
                }

                public static class MinPrice {
                    private double amount;
                    private Unit unit;
                    private UpdateStatus updateStatus;

                    public double getAmount() {
                        return amount;
                    }

                    public void setAmount(double value) {
                        this.amount = value;
                    }

                    public Unit getUnit() {
                        return unit;
                    }

                    public void setUnit(Unit value) {
                        this.unit = value;
                    }

                    public UpdateStatus getUpdateStatus() {
                        return updateStatus;
                    }

                    public void setUpdateStatus(UpdateStatus value) {
                        this.updateStatus = value;
                    }
                }

                public static class BoundLeg {
                    private long originPlaceId;
                    private long destinationPlaceId;
                    private DepartureDateTime departureDateTime;
                    private String quoteCreationTimestamp;
                    private String marketingCarrierId;

                    public long getOriginPlaceId() {
                        return originPlaceId;
                    }

                    public void setOriginPlaceId(long value) {
                        this.originPlaceId = value;
                    }

                    public long getDestinationPlaceId() {
                        return destinationPlaceId;
                    }

                    public void setDestinationPlaceId(long value) {
                        this.destinationPlaceId = value;
                    }

                    public DepartureDateTime getDepartureDateTime() {
                        return departureDateTime;
                    }

                    public void setDepartureDateTime(DepartureDateTime value) {
                        this.departureDateTime = value;
                    }

                    public String getQuoteCreationTimestamp() {
                        return quoteCreationTimestamp;
                    }

                    public void setQuoteCreationTimestamp(String value) {
                        this.quoteCreationTimestamp = value;
                    }

                    public String getMarketingCarrierId() {
                        return marketingCarrierId;
                    }

                    public void setMarketingCarrierId(String value) {
                        this.marketingCarrierId = value;
                    }

                    public static class DepartureDateTime {
                        private int year;
                        private int month;
                        private int day;
                        private int hour;
                        private int minute;
                        private int second;

                        public int getYear() {
                            return year;
                        }

                        public void setYear(int value) {
                            this.year = value;
                        }

                        public int getMonth() {
                            return month;
                        }

                        public void setMonth(int value) {
                            this.month = value;
                        }

                        public int getDay() {
                            return day;
                        }

                        public void setDay(int value) {
                            this.day = value;
                        }

                        public int getHour() {
                            return hour;
                        }

                        public void setHour(int value) {
                            this.hour = value;
                        }

                        public int getMinute() {
                            return minute;
                        }

                        public void setMinute(int value) {
                            this.minute = value;
                        }

                        public int getSecond() {
                            return second;
                        }

                        public void setSecond(int value) {
                            this.second = value;
                        }
                    }
                }
            }

            public static class Carrier {
                private String name;
                private String imageUrl;
                private String iata;

                public String getName() {
                    return name;
                }

                public void setName(String value) {
                    this.name = value;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String value) {
                    this.imageUrl = value;
                }

                public String getIata() {
                    return iata;
                }

                public void setIata(String value) {
                    this.iata = value;
                }
            }

            public static class Place {
                private String entityId;
                private String parentId;
                private String name;
                private String type;
                private String iata;
                private Coordinates coordinates;

                public String getEntityId() {
                    return entityId;
                }

                public void setEntityId(String value) {
                    this.entityId = value;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String value) {
                    this.parentId = value;
                }

                public String getName() {
                    return name;
                }

                public void setName(String value) {
                    this.name = value;
                }

                public String getType() {
                    return type;
                }

                public void setType(String value) {
                    this.type = value;
                }

                public String getIata() {
                    return iata;
                }

                public void setIata(String value) {
                    this.iata = value;
                }

                public Coordinates getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(Coordinates value) {
                    this.coordinates = value;
                }

                public static class Coordinates {
                    private double latitude;
                    private double longitude;

                    public double getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(double value) {
                        this.latitude = value;
                    }

                    public double getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(double value) {
                        this.longitude = value;
                    }
                }
            }
        }

        public static class GroupingOptions {
            private ByRoute byRoute;
            private ByDate byDate;

            public ByRoute getByRoute() {
                return byRoute;
            }

            public void setByRoute(ByRoute value) {
                this.byRoute = value;
            }

            public ByDate getByDate() {
                return byDate;
            }

            public void setByDate(ByDate value) {
                this.byDate = value;
            }

            public static class ByRoute {
                private QuotesGroup[] quotesGroups;

                public QuotesGroup[] getQuotesGroups() {
                    return quotesGroups;
                }

                public void setQuotesGroups(QuotesGroup[] value) {
                    this.quotesGroups = value;
                }

                public static class QuotesGroup {
                    private String originPlaceId;
                    private String destinationPlaceId;
                    private String[] quoteIds;

                    public String getOriginPlaceId() {
                        return originPlaceId;
                    }

                    public void setOriginPlaceId(String value) {
                        this.originPlaceId = value;
                    }

                    public String getDestinationPlaceId() {
                        return destinationPlaceId;
                    }

                    public void setDestinationPlaceId(String value) {
                        this.destinationPlaceId = value;
                    }

                    public String[] getQuoteIds() {
                        return quoteIds;
                    }

                    public void setQuoteIds(String[] value) {
                        this.quoteIds = value;
                    }
                }
            }

            public static class ByDate {
                private Object[] quotesInboundGroups;
                private QuotesOutboundGroup[] quotesOutboundGroups;

                public Object[] getQuotesInboundGroups() {
                    return quotesInboundGroups;
                }

                public void setQuotesInboundGroups(Object[] value) {
                    this.quotesInboundGroups = value;
                }

                public QuotesOutboundGroup[] getQuotesOutboundGroups() {
                    return quotesOutboundGroups;
                }

                public void setQuotesOutboundGroups(QuotesOutboundGroup[] value) {
                    this.quotesOutboundGroups = value;
                }

                public static class QuotesOutboundGroup {
                    private MonthYearDate monthYearDate;
                    private String[] quoteIds;

                    public MonthYearDate getMonthYearDate() {
                        return monthYearDate;
                    }

                    public void setMonthYearDate(MonthYearDate value) {
                        this.monthYearDate = value;
                    }

                    public String[] getQuoteIds() {
                        return quoteIds;
                    }

                    public void setQuoteIds(String[] value) {
                        this.quoteIds = value;
                    }

                    public static class MonthYearDate {
                        private long year;
                        private long month;
                        private long day;

                        public long getYear() {
                            return year;
                        }

                        public void setYear(long value) {
                            this.year = value;
                        }

                        public long getMonth() {
                            return month;
                        }

                        public void setMonth(long value) {
                            this.month = value;
                        }

                        public long getDay() {
                            return day;
                        }

                        public void setDay(long value) {
                            this.day = value;
                        }
                    }
                }
            }
        }
    }
}