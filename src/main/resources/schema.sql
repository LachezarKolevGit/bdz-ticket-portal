--
-- PostgreSQL database dump
--

-- Dumped from database version 12.15 (Ubuntu 12.15-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.15 (Ubuntu 12.15-0ubuntu0.20.04.1)

-- Started on 2023-05-30 14:05:34 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 24814)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24782)
-- Name: route_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.route_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.route_id_seq OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 24678)
-- Name: route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.route (
    id bigint DEFAULT nextval('public.route_id_seq'::regclass) NOT NULL
);


ALTER TABLE public.route OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 24791)
-- Name: seat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seat_id_seq
    START WITH 1
    INCREMENT BY 60
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seat_id_seq OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 24683)
-- Name: seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seat (
    id bigint DEFAULT nextval('public.seat_id_seq'::regclass) NOT NULL,
    seat_state smallint,
    ticket_id bigint,
    train_carriage_id bigint
);


ALTER TABLE public.seat OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 24794)
-- Name: ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ticket_id_seq
    START WITH 1
    INCREMENT BY 60
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ticket_id_seq OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 24688)
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ticket (
    id bigint DEFAULT nextval('public.ticket_id_seq'::regclass) NOT NULL,
    price double precision NOT NULL,
    purchased_at timestamp(6) without time zone,
    user_id bigint,
    ticket_state smallint
);


ALTER TABLE public.ticket OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 24748)
-- Name: train_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.train_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.train_id_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 24693)
-- Name: train; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.train (
    id bigint DEFAULT nextval('public.train_id_seq'::regclass) NOT NULL,
    arriving_at timestamp(6) without time zone,
    departing_at timestamp(6) without time zone,
    route_id bigint
);


ALTER TABLE public.train OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 24788)
-- Name: train_carriage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.train_carriage_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.train_carriage_id_seq OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24698)
-- Name: train_carriage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.train_carriage (
    id bigint DEFAULT nextval('public.train_carriage_id_seq'::regclass) NOT NULL,
    total_seats integer NOT NULL,
    train_carriage_type character varying(255),
    train_id bigint
);


ALTER TABLE public.train_carriage OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 24785)
-- Name: train_station_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.train_station_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.train_station_id_seq OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 24703)
-- Name: train_station; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.train_station (
    id bigint DEFAULT nextval('public.train_station_id_seq'::regclass) NOT NULL,
    latitude double precision,
    longitude double precision,
    name character varying(255),
    route_id bigint,
    train_stations_order integer
);


ALTER TABLE public.train_station OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24797)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 60
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 24708)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint DEFAULT nextval('public.users_id_seq'::regclass) NOT NULL,
    age integer NOT NULL,
    child_birth_year date,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    married boolean NOT NULL,
    password character varying(255),
    role smallint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3045 (class 0 OID 24814)
-- Dependencies: 216
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.flyway_schema_history VALUES (1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'postgres', '2023-05-29 15:16:45.909974', 0, true);


--
-- TOC entry 3031 (class 0 OID 24678)
-- Dependencies: 202
-- Data for Name: route; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.route VALUES (452);
INSERT INTO public.route VALUES (502);
INSERT INTO public.route VALUES (802);
INSERT INTO public.route VALUES (852);
INSERT INTO public.route VALUES (902);
INSERT INTO public.route VALUES (952);
INSERT INTO public.route VALUES (1202);
INSERT INTO public.route VALUES (1302);
INSERT INTO public.route VALUES (1402);
INSERT INTO public.route VALUES (1502);


--
-- TOC entry 3032 (class 0 OID 24683)
-- Dependencies: 203
-- Data for Name: seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.seat VALUES (2102, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2103, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2104, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2105, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2106, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2107, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2108, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2109, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2110, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2111, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2112, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2113, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2114, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2115, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2116, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2117, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2118, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2119, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2120, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2121, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2122, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2123, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2124, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2125, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2126, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2127, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2128, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2129, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2130, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2131, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2132, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2133, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2134, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2135, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2136, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2137, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2138, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2139, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2140, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2141, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2142, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2143, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2144, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2145, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2146, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2147, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2148, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2149, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2150, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2151, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2152, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2153, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2154, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2155, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2156, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2157, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2158, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2159, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2160, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2161, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2162, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2163, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2164, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2165, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2166, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2167, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2168, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2169, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2170, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2171, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2172, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2173, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2174, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2175, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2176, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2177, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2178, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2179, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2180, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2181, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2182, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2183, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2184, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2185, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2186, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2187, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2188, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2189, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2190, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2191, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2192, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2193, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2194, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2195, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2196, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2197, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2198, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2199, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2200, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2201, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2202, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2203, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2204, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2205, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2206, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2207, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2208, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2209, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2210, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2211, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2212, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2213, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2214, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2215, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2216, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2217, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2218, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2219, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2220, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2221, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2222, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2223, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2224, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2225, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2226, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2227, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2228, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2229, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2230, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2231, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2232, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2233, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2252, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2253, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2254, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2255, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2256, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2257, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2258, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2259, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2260, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2261, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2262, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2263, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2264, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2265, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2266, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2267, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2268, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2269, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2270, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2271, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2272, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2273, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2274, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2275, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2276, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2277, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2278, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2279, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2280, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2281, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2282, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2283, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2284, 1, NULL, NULL);
INSERT INTO public.seat VALUES (2302, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2303, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2304, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2305, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2306, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2307, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2308, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2309, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2310, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2311, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2312, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2313, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2314, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2315, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2316, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2317, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2318, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2319, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2320, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2321, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2322, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2323, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2324, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2325, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2326, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2327, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2328, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2329, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2330, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2331, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2332, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2333, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2334, 1, NULL, 1102);
INSERT INTO public.seat VALUES (2352, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2353, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2354, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2355, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2356, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2357, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2358, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2359, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2360, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2361, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2362, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2363, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2364, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2365, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2366, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2367, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2368, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2369, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2370, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2371, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2372, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2373, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2374, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2375, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2376, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2377, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2378, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2379, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2380, 1, NULL, 1152);
INSERT INTO public.seat VALUES (2381, 1, NULL, 1152);


--
-- TOC entry 3033 (class 0 OID 24688)
-- Dependencies: 204
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3034 (class 0 OID 24693)
-- Dependencies: 205
-- Data for Name: train; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.train VALUES (2, '2023-05-26 10:20:00', '2023-05-25 07:30:00', 452);


--
-- TOC entry 3035 (class 0 OID 24698)
-- Dependencies: 206
-- Data for Name: train_carriage; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.train_carriage VALUES (902, 0, NULL, NULL);
INSERT INTO public.train_carriage VALUES (952, 0, NULL, NULL);
INSERT INTO public.train_carriage VALUES (1002, 33, 'CLASS_B', NULL);
INSERT INTO public.train_carriage VALUES (1003, 33, 'CLASS_B', NULL);
INSERT INTO public.train_carriage VALUES (1004, 33, 'SLEEPER', NULL);
INSERT INTO public.train_carriage VALUES (1005, 33, 'CLASS_A', NULL);
INSERT INTO public.train_carriage VALUES (1052, 33, 'CLASS_B', NULL);
INSERT INTO public.train_carriage VALUES (1102, 33, 'CLASS_B', NULL);
INSERT INTO public.train_carriage VALUES (1152, 30, 'CLASS_B', 2);


--
-- TOC entry 3036 (class 0 OID 24703)
-- Dependencies: 207
-- Data for Name: train_station; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.train_station VALUES (452, 42.712115, 23.321046, 'Sofia Central station', 452, 0);
INSERT INTO public.train_station VALUES (453, 42.134444, 24.741389, 'Plovdiv Central railway station', 452, 1);
INSERT INTO public.train_station VALUES (454, 42.490833, 27.4725, 'Burgas Central railway station', 452, 2);
INSERT INTO public.train_station VALUES (502, 42.712115, 23.321046, 'Sofia Central station', 502, 0);
INSERT INTO public.train_station VALUES (503, 42.134444, 24.741389, 'Plovdiv Central railway station', 502, 1);
INSERT INTO public.train_station VALUES (504, 42.490833, 27.4725, 'Burgas Central railway station', 502, 2);
INSERT INTO public.train_station VALUES (1452, 42.712115, 23.321046, 'Sofia Central station', 1502, 0);
INSERT INTO public.train_station VALUES (1453, 42.134444, 24.741389, 'Plovdiv Central railway station', 1502, 1);
INSERT INTO public.train_station VALUES (1454, 42.490833, 27.4725, 'Burgas Central railway station', 1502, 2);
INSERT INTO public.train_station VALUES (802, 42.712115, 23.321046, 'Sofia Central station', 802, 0);
INSERT INTO public.train_station VALUES (803, 42.134444, 24.741389, 'Plovdiv Central railway station', 802, 1);
INSERT INTO public.train_station VALUES (804, 42.490833, 27.4725, 'Burgas Central railway station', 802, 2);
INSERT INTO public.train_station VALUES (852, 42.712115, 23.321046, 'Sofia Central station', 902, 0);
INSERT INTO public.train_station VALUES (853, 42.134444, 24.741389, 'Plovdiv Central railway station', 902, 1);
INSERT INTO public.train_station VALUES (854, 42.490833, 27.4725, 'Burgas Central railway station', 902, 2);
INSERT INTO public.train_station VALUES (902, 42.712115, 23.321046, 'Sofia Central station', 952, 0);
INSERT INTO public.train_station VALUES (903, 42.134444, 24.741389, 'Plovdiv Central railway station', 952, 1);
INSERT INTO public.train_station VALUES (904, 42.490833, 27.4725, 'Burgas Central railway station', 952, 2);
INSERT INTO public.train_station VALUES (1152, 42.712115, 23.321046, 'Sofia Central station', 1202, 0);
INSERT INTO public.train_station VALUES (1153, 42.134444, 24.741389, 'Plovdiv Central railway station', 1202, 1);
INSERT INTO public.train_station VALUES (1154, 42.490833, 27.4725, 'Burgas Central railway station', 1202, 2);
INSERT INTO public.train_station VALUES (1352, 42.712115, 23.321046, 'Sofia Central station', 1402, 0);
INSERT INTO public.train_station VALUES (1353, 42.134444, 24.741389, 'Plovdiv Central railway station', 1402, 1);
INSERT INTO public.train_station VALUES (1354, 42.490833, 27.4725, 'Burgas Central railway station', 1402, 2);
INSERT INTO public.train_station VALUES (1252, 42.712115, 23.321046, 'Sofia Central station', 1302, 0);
INSERT INTO public.train_station VALUES (1253, 42.134444, 24.741389, 'Plovdiv Central railway station', 1302, 1);
INSERT INTO public.train_station VALUES (1254, 42.490833, 27.4725, 'Burgas Central railway station', 1302, 2);


--
-- TOC entry 3037 (class 0 OID 24708)
-- Dependencies: 208
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (2, 0, NULL, NULL, NULL, NULL, false, '$2a$10$NzQNARtJbY/D2KOush3qp.jMD2h4x.pvWsHWHDpw9RUEo0dtoMnkC', 0);
INSERT INTO public.users VALUES (52, 0, NULL, '', '', '', false, '$2a$10$nsp5Vi8onR2/QOoDX.J3QeSd8lM8XvINRLTNdPZZd2tGsYAzT.ml2', 0);
INSERT INTO public.users VALUES (102, 0, NULL, NULL, NULL, NULL, false, NULL, NULL);
INSERT INTO public.users VALUES (352, 0, NULL, 'gosho@abv.bg', 'Lachezar', 'Kolev', false, '$2a$10$EeOdCIqfAR95cNsY7C55J.lAorzsrtFMzkGdQ6QuOVlCMmQUG3hi6', 1);
INSERT INTO public.users VALUES (302, 22, '2010-10-01', 'luchezarkolev@gmail.com', 'Lachezar', 'Kolev', true, '$2a$10$aeWeefYfG7K3lL0lIYe7GeujcV0.ZsyzPviDYysd/idPMv4sWFQ6W', 0);


--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 210
-- Name: route_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.route_id_seq', 1, false);


--
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 213
-- Name: seat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seat_id_seq', 1, false);


--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 214
-- Name: ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ticket_id_seq', 1, false);


--
-- TOC entry 3054 (class 0 OID 0)
-- Dependencies: 212
-- Name: train_carriage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.train_carriage_id_seq', 1, false);


--
-- TOC entry 3055 (class 0 OID 0)
-- Dependencies: 209
-- Name: train_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.train_id_seq', 2, true);


--
-- TOC entry 3056 (class 0 OID 0)
-- Dependencies: 211
-- Name: train_station_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.train_station_id_seq', 1, false);


--
-- TOC entry 3057 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- TOC entry 2897 (class 2606 OID 24822)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 2883 (class 2606 OID 24682)
-- Name: route route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_pkey PRIMARY KEY (id);


--
-- TOC entry 2885 (class 2606 OID 24687)
-- Name: seat seat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_pkey PRIMARY KEY (id);


--
-- TOC entry 2887 (class 2606 OID 24692)
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- TOC entry 2891 (class 2606 OID 24702)
-- Name: train_carriage train_carriage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_carriage
    ADD CONSTRAINT train_carriage_pkey PRIMARY KEY (id);


--
-- TOC entry 2889 (class 2606 OID 24697)
-- Name: train train_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train
    ADD CONSTRAINT train_pkey PRIMARY KEY (id);


--
-- TOC entry 2893 (class 2606 OID 24707)
-- Name: train_station train_station_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_station
    ADD CONSTRAINT train_station_pkey PRIMARY KEY (id);


--
-- TOC entry 2895 (class 2606 OID 24715)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2898 (class 1259 OID 24823)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 2902 (class 2606 OID 24731)
-- Name: train fk189619hk9k3ovx3iu8s9n7t3i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train
    ADD CONSTRAINT fk189619hk9k3ovx3iu8s9n7t3i FOREIGN KEY (route_id) REFERENCES public.route(id);


--
-- TOC entry 2899 (class 2606 OID 24716)
-- Name: seat fk8ngrgsadp7q1lcakg3kkdmvqj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT fk8ngrgsadp7q1lcakg3kkdmvqj FOREIGN KEY (ticket_id) REFERENCES public.ticket(id);


--
-- TOC entry 2903 (class 2606 OID 24736)
-- Name: train_carriage fke30vv1a2lwc4phciwglvu21l4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_carriage
    ADD CONSTRAINT fke30vv1a2lwc4phciwglvu21l4 FOREIGN KEY (train_id) REFERENCES public.train(id);


--
-- TOC entry 2900 (class 2606 OID 24721)
-- Name: seat fkijny9i6w4att2e6sppt5vuswq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT fkijny9i6w4att2e6sppt5vuswq FOREIGN KEY (train_carriage_id) REFERENCES public.train_carriage(id);


--
-- TOC entry 2901 (class 2606 OID 24726)
-- Name: ticket fkmvugyjf7b45u0juyue7k3pct0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT fkmvugyjf7b45u0juyue7k3pct0 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 2904 (class 2606 OID 24741)
-- Name: train_station fkoif7j5hpkqr6bpt9e4p21d181; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_station
    ADD CONSTRAINT fkoif7j5hpkqr6bpt9e4p21d181 FOREIGN KEY (route_id) REFERENCES public.route(id);


-- Completed on 2023-05-30 14:05:34 EEST

--
-- PostgreSQL database dump complete
--

