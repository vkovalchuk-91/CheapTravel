package org.geekhub.kovalchuk.json.entity;

import java.util.ArrayList;

public class MonthPriceJsonRequest {
    public Query query;

    public MonthPriceJsonRequest(String currency, long originPlaceId, long destinationPlaceId, int year, int month) {
        this.query = new Query(currency, originPlaceId, destinationPlaceId, year, month);
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public static class Query{
        public String market;
        public String locale;
        public String currency;
        public String dateTimeGroupingType;
        public ArrayList<QueryLeg> queryLegs;

        public Query(String currency, long originPlaceId, long destinationPlaceId, int year, int month) {
            this.market = "UA";
            this.locale = "uk-UA";
            this.currency = currency;
            this.dateTimeGroupingType = "DATE_TIME_GROUPING_TYPE_BY_DATE";
            this.queryLegs = new ArrayList<>();
            queryLegs.add(new QueryLeg(originPlaceId, destinationPlaceId, year, month));
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public String getDateTimeGroupingType() {
            return dateTimeGroupingType;
        }

        public void setDateTimeGroupingType(String dateTimeGroupingType) {
            this.dateTimeGroupingType = dateTimeGroupingType;
        }

        public ArrayList<QueryLeg> getQueryLegs() {
            return queryLegs;
        }

        public void setQueryLegs(ArrayList<QueryLeg> queryLegs) {
            this.queryLegs = queryLegs;
        }

        public static class QueryLeg {
            public OriginPlace originPlace;
            public DestinationPlace destinationPlace;
            public DateRange date_range;

            public QueryLeg(long originPlaceId, long destinationPlaceId, int year, int month) {
                this.originPlace = new OriginPlace(originPlaceId);
                this.destinationPlace = new DestinationPlace(destinationPlaceId);
                this.date_range = new DateRange(year, month);
            }

            public OriginPlace getOriginPlace() {
                return originPlace;
            }

            public void setOriginPlace(OriginPlace originPlace) {
                this.originPlace = originPlace;
            }

            public DestinationPlace getDestinationPlace() {
                return destinationPlace;
            }

            public void setDestinationPlace(DestinationPlace destinationPlace) {
                this.destinationPlace = destinationPlace;
            }

            public DateRange getDate_range() {
                return date_range;
            }

            public void setDate_range(DateRange date_range) {
                this.date_range = date_range;
            }

            public static class OriginPlace{
                public QueryPlace queryPlace;

                public OriginPlace(long originPlaceId) {
                    this.queryPlace = new QueryPlace(String.valueOf(originPlaceId));
                }

                public QueryPlace getQueryPlace() {
                    return queryPlace;
                }

                public void setQueryPlace(QueryPlace queryPlace) {
                    this.queryPlace = queryPlace;
                }
            }

            public static class DestinationPlace{
                public QueryPlace queryPlace;

                public DestinationPlace(long destinationPlaceId) {
                    this.queryPlace = new QueryPlace(String.valueOf(destinationPlaceId));
                }

                public QueryPlace getQueryPlace() {
                    return queryPlace;
                }

                public void setQueryPlace(QueryPlace queryPlace) {
                    this.queryPlace = queryPlace;
                }
            }

            public static class QueryPlace{

                public String entityId;

                public QueryPlace(String entityId) {
                    this.entityId = entityId;
                }

                public String getEntityId() {
                    return entityId;
                }

                public void setEntityId(String entityId) {
                    this.entityId = entityId;
                }
            }

            public class DateRange{
                public StartDate startDate;
                public EndDate endDate;

                public DateRange(int year, int month) {
                    this.startDate = new StartDate(year, month);
                    this.endDate = new EndDate(year, month);
                }

                public StartDate getStartDate() {
                    return startDate;
                }

                public void setStartDate(StartDate startDate) {
                    this.startDate = startDate;
                }

                public EndDate getEndDate() {
                    return endDate;
                }

                public void setEndDate(EndDate endDate) {
                    this.endDate = endDate;
                }

                public class StartDate{
                    public int year;
                    public int month;

                    public StartDate(int year, int month) {
                        this.year = year;
                        this.month = month;
                    }

                    public int getYear() {
                        return year;
                    }

                    public void setYear(int year) {
                        this.year = year;
                    }

                    public int getMonth() {
                        return month;
                    }

                    public void setMonth(int month) {
                        this.month = month;
                    }
                }

                public class EndDate{
                    public int year;
                    public int month;

                    public EndDate(int year, int month) {
                        this.year = year;
                        this.month = month;
                    }

                    public int getYear() {
                        return year;
                    }

                    public void setYear(int year) {
                        this.year = year;
                    }

                    public int getMonth() {
                        return month;
                    }

                    public void setMonth(int month) {
                        this.month = month;
                    }
                }
            }
        }
    }
}