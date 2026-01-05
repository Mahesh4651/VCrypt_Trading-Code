package com.Mrudul.SpringCrypto.service;

import java.util.*;

public class CoinImageMapper {

    private static final Map<String, String> imageMap = new HashMap<>();

    // Define targetSymbols with lowercase symbols suffixed with "usd"
    private final Set<String> targetSymbols;

    // Constructor to initialize targetSymbols
    public CoinImageMapper() {
        // Extract symbols from imageMap, convert to lowercase, and add "usd" suffix
        Set<String> symbolsWithUsd = new HashSet<>();
        for (String symbol : imageMap.keySet()) {
            symbolsWithUsd.add(symbol.toLowerCase() + "usd");
        }

        // Add any additional symbols not in imageMap (if needed)
        symbolsWithUsd.addAll(Arrays.asList(
                // Add symbols here that are not in CoinImageMapper but should be in targetSymbols
                "exampleusd" // Placeholder; replace with actual symbols
        ));

        this.targetSymbols = symbolsWithUsd;
    }

    static {
        imageMap.put("1000CAT", "https://s2.coinmarketcap.com/static/img/coins/64x64/12345.png");
        imageMap.put("1000CHEEMS", "https://s2.coinmarketcap.com/static/img/coins/64x64/12346.png");
        imageMap.put("1000SATS", "https://s2.coinmarketcap.com/static/img/coins/64x64/12347.png");
        imageMap.put("1INCH", "https://s2.coinmarketcap.com/static/img/coins/64x64/8104.png");
        imageMap.put("1MBABYDOGE", "https://s2.coinmarketcap.com/static/img/coins/64x64/12348.png");
        imageMap.put("1MBABYDOGEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12349.png");
        imageMap.put("AAVE", "https://s2.coinmarketcap.com/static/img/coins/64x64/7278.png");
        imageMap.put("ACA", "https://s2.coinmarketcap.com/static/img/coins/64x64/6756.png");
        imageMap.put("ACE", "https://s2.coinmarketcap.com/static/img/coins/64x64/12350.png");
        imageMap.put("ACH", "https://s2.coinmarketcap.com/static/img/coins/64x64/6958.png");
        imageMap.put("ACM", "https://s2.coinmarketcap.com/static/img/coins/64x64/5226.png");
        imageMap.put("ACT", "https://s2.coinmarketcap.com/static/img/coins/64x64/12351.png");
        imageMap.put("ACX", "https://s2.coinmarketcap.com/static/img/coins/64x64/12352.png");
        imageMap.put("ADA", "https://s2.coinmarketcap.com/static/img/coins/64x64/2010.png");
        imageMap.put("ADAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12353.png");
        imageMap.put("ADX", "https://s2.coinmarketcap.com/static/img/coins/64x64/1768.png");
        imageMap.put("AEVO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12354.png");
        imageMap.put("AI", "https://s2.coinmarketcap.com/static/img/coins/64x64/12355.png");
        imageMap.put("AIXBT", "https://s2.coinmarketcap.com/static/img/coins/64x64/12356.png");
        imageMap.put("ALCX", "https://s2.coinmarketcap.com/static/img/coins/64x64/6783.png");
        imageMap.put("ALGO", "https://s2.coinmarketcap.com/static/img/coins/64x64/4030.png");
        imageMap.put("ALPACA", "https://s2.coinmarketcap.com/static/img/coins/64x64/8707.png");
        imageMap.put("ALPHA", "https://s2.coinmarketcap.com/static/img/coins/64x64/7232.png");
        imageMap.put("ALT", "https://s2.coinmarketcap.com/static/img/coins/64x64/12357.png");
        imageMap.put("AMP", "https://s2.coinmarketcap.com/static/img/coins/64x64/6945.png");
        imageMap.put("ANIME", "https://s2.coinmarketcap.com/static/img/coins/64x64/12358.png");
        imageMap.put("APE", "https://s2.coinmarketcap.com/static/img/coins/64x64/18876.png");
        imageMap.put("APT", "https://s2.coinmarketcap.com/static/img/coins/64x64/21794.png");
        imageMap.put("APTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12359.png");
        imageMap.put("AR", "https://s2.coinmarketcap.com/static/img/coins/64x64/5632.png");
        imageMap.put("ARB", "https://s2.coinmarketcap.com/static/img/coins/64x64/11841.png");
        imageMap.put("ARBFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12360.png");
        imageMap.put("ARK", "https://s2.coinmarketcap.com/static/img/coins/64x64/1586.png");
        imageMap.put("ARKM", "https://s2.coinmarketcap.com/static/img/coins/64x64/12361.png");
        imageMap.put("ARPA", "https://s2.coinmarketcap.com/static/img/coins/64x64/4039.png");
        imageMap.put("ASR", "https://s2.coinmarketcap.com/static/img/coins/64x64/5227.png");
        imageMap.put("AST", "https://s2.coinmarketcap.com/static/img/coins/64x64/2243.png");
        imageMap.put("ATA", "https://s2.coinmarketcap.com/static/img/coins/64x64/9875.png");
        imageMap.put("ATM", "https://s2.coinmarketcap.com/static/img/coins/64x64/5229.png");
        imageMap.put("ATOM", "https://s2.coinmarketcap.com/static/img/coins/64x64/3794.png");
        imageMap.put("ATOMFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12362.png");
        imageMap.put("AUCTION", "https://s2.coinmarketcap.com/static/img/coins/64x64/8602.png");
        imageMap.put("AUDIO", "https://s2.coinmarketcap.com/static/img/coins/64x64/7455.png");
        imageMap.put("AVA", "https://s2.coinmarketcap.com/static/img/coins/64x64/12363.png");
        imageMap.put("AVAX", "https://s2.coinmarketcap.com/static/img/coins/64x64/5805.png");
        imageMap.put("AVAXFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12364.png");
        imageMap.put("AXL", "https://s2.coinmarketcap.com/static/img/coins/64x64/12365.png");
        imageMap.put("BADGER", "https://s2.coinmarketcap.com/static/img/coins/64x64/7859.png");
        imageMap.put("BAKE", "https://s2.coinmarketcap.com/static/img/coins/64x64/7064.png");
        imageMap.put("BANANA", "https://s2.coinmarketcap.com/static/img/coins/64x64/12366.png");
        imageMap.put("BAND", "https://s2.coinmarketcap.com/static/img/coins/64x64/4679.png");
        imageMap.put("BAR", "https://s2.coinmarketcap.com/static/img/coins/64x64/5228.png");
        imageMap.put("BAT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1697.png"); // Basic Attention Token
        imageMap.put("BB", "https://s2.coinmarketcap.com/static/img/coins/64x64/12345.png"); // BB Coin
        imageMap.put("BCH", "https://s2.coinmarketcap.com/static/img/coins/64x64/1831.png"); // Bitcoin Cash
        imageMap.put("BEAMX", "https://s2.coinmarketcap.com/static/img/coins/64x64/12346.png"); // BeamX
        imageMap.put("BEL", "https://s2.coinmarketcap.com/static/img/coins/64x64/6928.png"); // Bella Protocol
        imageMap.put("BERA", "https://s2.coinmarketcap.com/static/img/coins/64x64/12347.png"); // Bera Coin
        imageMap.put("BETA", "https://s2.coinmarketcap.com/static/img/coins/64x64/12348.png"); // Beta Finance
        imageMap.put("BICO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12349.png"); // Biconomy
        imageMap.put("BIFI", "https://s2.coinmarketcap.com/static/img/coins/64x64/12350.png"); // Beefy.Finance
        imageMap.put("BIO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12351.png"); // BioCoin
        imageMap.put("BIOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12352.png"); // BioCoin FD
        imageMap.put("BNB", "https://s2.coinmarketcap.com/static/img/coins/64x64/1839.png"); // Binance Coin
        imageMap.put("BNBFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12353.png"); // Binance Coin FD
        imageMap.put("BNT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1727.png"); // Bancor
        imageMap.put("BNX", "https://s2.coinmarketcap.com/static/img/coins/64x64/12354.png"); // BinaryX
        imageMap.put("BOME", "https://s2.coinmarketcap.com/static/img/coins/64x64/12355.png"); // Bome Coin
        imageMap.put("BOMEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12356.png"); // Bome Coin FD
        imageMap.put("BONK", "https://s2.coinmarketcap.com/static/img/coins/64x64/12357.png"); // Bonk
        imageMap.put("BTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"); // Bitcoin
        imageMap.put("BTCFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"); // Bitcoin FD
        imageMap.put("BTTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/12359.png"); // BitTorrent
        imageMap.put("BURGER", "https://s2.coinmarketcap.com/static/img/coins/64x64/12360.png"); // Burger Swap
        imageMap.put("C", "https://s2.coinmarketcap.com/static/img/coins/64x64/12361.png"); // C Token
        imageMap.put("C98", "https://s2.coinmarketcap.com/static/img/coins/64x64/12362.png"); // Coin98
        imageMap.put("CAKE", "https://s2.coinmarketcap.com/static/img/coins/64x64/12363.png"); // PancakeSwap
        imageMap.put("CATI", "https://s2.coinmarketcap.com/static/img/coins/64x64/12364.png"); // CATI Coin
        imageMap.put("CATIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12365.png"); // CATI Coin FD
        imageMap.put("CELO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12366.png"); // Celo
        imageMap.put("CELR", "https://s2.coinmarketcap.com/static/img/coins/64x64/12367.png"); // Celer Network
        imageMap.put("CETUS", "https://s2.coinmarketcap.com/static/img/coins/64x64/12368.png"); // Cetus
        imageMap.put("CFX", "https://s2.coinmarketcap.com/static/img/coins/64x64/12369.png"); // Conflux
        imageMap.put("CGPT", "https://s2.coinmarketcap.com/static/img/coins/64x64/12370.png"); // ChatGPT Token
        imageMap.put("CHESS", "https://s2.coinmarketcap.com/static/img/coins/64x64/12371.png"); // Tranchess
        imageMap.put("CHZ", "https://s2.coinmarketcap.com/static/img/coins/64x64/4066.png"); // Chiliz
        imageMap.put("CITY", "https://s2.coinmarketcap.com/static/img/coins/64x64/12372.png"); // Manchester City Fan Token
        imageMap.put("CKB", "https://s2.coinmarketcap.com/static/img/coins/64x64/12373.png"); // Nervos Network
        imageMap.put("COMBO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12374.png"); // Furucombo
        imageMap.put("COMP", "https://s2.coinmarketcap.com/static/img/coins/64x64/5692.png"); // Compound
        imageMap.put("COOKIE", "https://s2.coinmarketcap.com/static/img/coins/64x64/12375.png"); // Cookie Token
        imageMap.put("COS", "https://s2.coinmarketcap.com/static/img/coins/64x64/12376.png"); // Contentos
        imageMap.put("COTI", "https://s2.coinmarketcap.com/static/img/coins/64x64/12377.png"); // COTI
        imageMap.put("COW", "https://s2.coinmarketcap.com/static/img/coins/64x64/12378.png"); // CoW Protocol
        imageMap.put("CREAM", "https://s2.coinmarketcap.com/static/img/coins/64x64/12379.png"); // Cream Finance
        imageMap.put("CRV", "https://s2.coinmarketcap.com/static/img/coins/64x64/6538.png"); // Curve DAO Token
        imageMap.put("CTK", "https://s2.coinmarketcap.com/static/img/coins/64x64/12380.png"); // CertiK
        imageMap.put("CTSI", "https://s2.coinmarketcap.com/static/img/coins/64x64/6632.png"); // Cartesi
        imageMap.put("CTXC", "https://s2.coinmarketcap.com/static/img/coins/64x64/2638.png"); // Cortex
        imageMap.put("CVC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1816.png"); // Civic
        imageMap.put("CVX", "https://s2.coinmarketcap.com/static/img/coins/64x64/9903.png"); // Convex Finance
        imageMap.put("DASH", "https://s2.coinmarketcap.com/static/img/coins/64x64/131.png"); // Dash
        imageMap.put("DCR", "https://s2.coinmarketcap.com/static/img/coins/64x64/1168.png"); // Decred
        imageMap.put("DEGO", "https://s2.coinmarketcap.com/static/img/coins/64x64/7080.png"); // Dego Finance
        imageMap.put("DENT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1886.png"); // Dent
        imageMap.put("DEXE", "https://s2.coinmarketcap.com/static/img/coins/64x64/7431.png"); // DeXe
        imageMap.put("DF", "https://s2.coinmarketcap.com/static/img/coins/64x64/5957.png"); // dForce
        imageMap.put("DGB", "https://s2.coinmarketcap.com/static/img/coins/64x64/109.png"); // DigiByte
        imageMap.put("DIA", "https://s2.coinmarketcap.com/static/img/coins/64x64/6138.png"); // DIA
        imageMap.put("DODO", "https://s2.coinmarketcap.com/static/img/coins/64x64/7224.png"); // DODO
        imageMap.put("DOGE", "https://s2.coinmarketcap.com/static/img/coins/64x64/74.png"); // Dogecoin
        imageMap.put("DOT", "https://s2.coinmarketcap.com/static/img/coins/64x64/6636.png"); // Polkadot
        imageMap.put("DYDX", "https://s2.coinmarketcap.com/static/img/coins/64x64/11156.png"); // dYdX
        imageMap.put("DYM", "https://s2.coinmarketcap.com/static/img/coins/64x64/28936.png"); // Dymension
        imageMap.put("EDU", "https://s2.coinmarketcap.com/static/img/coins/64x64/29179.png"); // Open Campus
        imageMap.put("EGLD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6892.png"); // MultiversX (Elrond)
        imageMap.put("EIGEN", "https://s2.coinmarketcap.com/static/img/coins/64x64/30246.png"); // EigenLayer
        imageMap.put("ELF", "https://s2.coinmarketcap.com/static/img/coins/64x64/2299.png"); // aelf
        imageMap.put("ENA", "https://s2.coinmarketcap.com/static/img/coins/64x64/29872.png"); // Ethena
        imageMap.put("ENS", "https://s2.coinmarketcap.com/static/img/coins/64x64/13855.png"); // Ethereum Name Service
        imageMap.put("EOS", "https://s2.coinmarketcap.com/static/img/coins/64x64/1765.png"); // EOS
        imageMap.put("ERN", "https://s2.coinmarketcap.com/static/img/coins/64x64/8615.png"); // Ethernity Chain
        imageMap.put("ETC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1321.png"); // Ethereum Classic
        imageMap.put("ETH", "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"); // Ethereum
        imageMap.put("FET", "https://s2.coinmarketcap.com/static/img/coins/64x64/3773.png"); // Fetch.ai
        imageMap.put("FIL", "https://s2.coinmarketcap.com/static/img/coins/64x64/2280.png"); // Filecoin
        imageMap.put("FLOKI", "https://s2.coinmarketcap.com/static/img/coins/64x64/10804.png"); // Floki Inu
        imageMap.put("FLUX", "https://s2.coinmarketcap.com/static/img/coins/64x64/3029.png"); // Flux
        imageMap.put("FTT", "https://s2.coinmarketcap.com/static/img/coins/64x64/4195.png"); // FTX Token
        imageMap.put("GALA", "https://s2.coinmarketcap.com/static/img/coins/64x64/7080.png"); // Gala
        imageMap.put("GRT", "https://s2.coinmarketcap.com/static/img/coins/64x64/6719.png"); // The Graph
        imageMap.put("HBAR", "https://s2.coinmarketcap.com/static/img/coins/64x64/4642.png"); // Hedera
        imageMap.put("ICP", "https://s2.coinmarketcap.com/static/img/coins/64x64/8916.png"); // Internet Computer
        imageMap.put("ID", "https://s2.coinmarketcap.com/static/img/coins/64x64/29180.png"); // Space ID
        imageMap.put("ILV", "https://s2.coinmarketcap.com/static/img/coins/64x64/8719.png"); // Illuvium
        imageMap.put("IMX", "https://s2.coinmarketcap.com/static/img/coins/64x64/10603.png"); // Immutable
        imageMap.put("INJ", "https://s2.coinmarketcap.com/static/img/coins/64x64/7226.png"); // Injective
        imageMap.put("IOTA", "https://s2.coinmarketcap.com/static/img/coins/64x64/1720.png"); // IOTA
        imageMap.put("JASMY", "https://s2.coinmarketcap.com/static/img/coins/64x64/8425.png"); // JasmyCoin
        imageMap.put("JOE", "https://s2.coinmarketcap.com/static/img/coins/64x64/11396.png"); // JOE
        imageMap.put("JUP", "https://s2.coinmarketcap.com/static/img/coins/64x64/23275.png"); // Jupiter
        imageMap.put("KAVA", "https://s2.coinmarketcap.com/static/img/coins/64x64/4846.png"); // Kava
        imageMap.put("KSM", "https://s2.coinmarketcap.com/static/img/coins/64x64/5034.png"); // Kusama
        imageMap.put("LDO", "https://s2.coinmarketcap.com/static/img/coins/64x64/8000.png"); // Lido DAO
        imageMap.put("LINA", "https://s2.coinmarketcap.com/static/img/coins/64x64/7102.png"); // Linear Finance
        imageMap.put("LINK", "https://s2.coinmarketcap.com/static/img/coins/64x64/1975.png"); // Chainlink
        imageMap.put("LPT", "https://s2.coinmarketcap.com/static/img/coins/64x64/3640.png"); // Livepeer
        imageMap.put("LQTY", "https://s2.coinmarketcap.com/static/img/coins/64x64/7501.png"); // Liquity
        imageMap.put("LTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/2.png"); // Litecoin
        imageMap.put("LTO", "https://s2.coinmarketcap.com/static/img/coins/64x64/3714.png"); // LTO Network
        imageMap.put("LUNA", "https://s2.coinmarketcap.com/static/img/coins/64x64/4172.png"); // Terra 2.0
        imageMap.put("LUNC", "https://s2.coinmarketcap.com/static/img/coins/64x64/4172.png"); // Terra Classic
        imageMap.put("MAGIC", "https://s2.coinmarketcap.com/static/img/coins/64x64/17274.png"); // MAGIC
        imageMap.put("MANA", "https://s2.coinmarketcap.com/static/img/coins/64x64/1966.png"); // Decentraland
        imageMap.put("MANTA", "https://s2.coinmarketcap.com/static/img/coins/64x64/29141.png"); // Manta Network
        imageMap.put("MASK", "https://s2.coinmarketcap.com/static/img/coins/64x64/8536.png"); // Mask Network
        imageMap.put("MAV", "https://s2.coinmarketcap.com/static/img/coins/64x64/29265.png"); // Maverick Protocol
        imageMap.put("MBL", "https://s2.coinmarketcap.com/static/img/coins/64x64/4038.png"); // MovieBloc
        imageMap.put("MBOX", "https://s2.coinmarketcap.com/static/img/coins/64x64/9175.png"); // Mobox
        imageMap.put("MEME", "https://s2.coinmarketcap.com/static/img/coins/64x64/30618.png"); // MEME Coin
        imageMap.put("METIS", "https://s2.coinmarketcap.com/static/img/coins/64x64/9640.png"); // MetisDAO
        imageMap.put("MINA", "https://s2.coinmarketcap.com/static/img/coins/64x64/8646.png"); // Mina Protocol
        imageMap.put("MKR", "https://s2.coinmarketcap.com/static/img/coins/64x64/1518.png"); // Maker
        imageMap.put("MOVE", "https://s2.coinmarketcap.com/static/img/coins/64x64/31709.png"); // Move Network
        imageMap.put("MOVR", "https://s2.coinmarketcap.com/static/img/coins/64x64/9285.png"); // Moonriver
        imageMap.put("NEAR", "https://s2.coinmarketcap.com/static/img/coins/64x64/6535.png"); // NEAR Protocol
        imageMap.put("NEO", "https://s2.coinmarketcap.com/static/img/coins/64x64/1376.png"); // Neo
        imageMap.put("NEXO", "https://s2.coinmarketcap.com/static/img/coins/64x64/2694.png"); // Nexo
        imageMap.put("NKN", "https://s2.coinmarketcap.com/static/img/coins/64x64/2780.png"); // NKN
        imageMap.put("NMR", "https://s2.coinmarketcap.com/static/img/coins/64x64/1732.png"); // Numeraire
        imageMap.put("NOT", "https://s2.coinmarketcap.com/static/img/coins/64x64/30952.png"); // Notcoin
        imageMap.put("NTRN", "https://s2.coinmarketcap.com/static/img/coins/64x64/29092.png"); // Neutron
        imageMap.put("NULS", "https://s2.coinmarketcap.com/static/img/coins/64x64/2092.png"); // Nuls
        imageMap.put("OG", "https://s2.coinmarketcap.com/static/img/coins/64x64/5229.png"); // OG Fan Token
        imageMap.put("OM", "https://s2.coinmarketcap.com/static/img/coins/64x64/6536.png"); // MANTRA DAO
        imageMap.put("ONE", "https://s2.coinmarketcap.com/static/img/coins/64x64/3945.png"); // Harmony
        imageMap.put("ONT", "https://s2.coinmarketcap.com/static/img/coins/64x64/2566.png"); // Ontology
        imageMap.put("OP", "https://s2.coinmarketcap.com/static/img/coins/64x64/11840.png"); // Optimism
        imageMap.put("ORDI", "https://s2.coinmarketcap.com/static/img/coins/64x64/28752.png"); // ORDI
        imageMap.put("OSMO", "https://s2.coinmarketcap.com/static/img/coins/64x64/12220.png"); // Osmosis
        imageMap.put("OXT", "https://s2.coinmarketcap.com/static/img/coins/64x64/5026.png"); // Orchid
        imageMap.put("PAXG", "https://s2.coinmarketcap.com/static/img/coins/64x64/4705.png"); // PAX Gold
        imageMap.put("PDA", "https://s2.coinmarketcap.com/static/img/coins/64x64/30578.png"); // Pocket Arena
        imageMap.put("PENDLE", "https://s2.coinmarketcap.com/static/img/coins/64x64/9499.png"); // Pendle
        imageMap.put("PEOPLE", "https://s2.coinmarketcap.com/static/img/coins/64x64/17751.png"); // ConstitutionDAO
        imageMap.put("PEPE", "https://s2.coinmarketcap.com/static/img/coins/64x64/24478.png"); // Pepe
        imageMap.put("PHA", "https://s2.coinmarketcap.com/static/img/coins/64x64/6841.png"); // Phala Network
        imageMap.put("PIVX", "https://s2.coinmarketcap.com/static/img/coins/64x64/1169.png"); // PIVX
        imageMap.put("PIXEL", "https://s2.coinmarketcap.com/static/img/coins/64x64/30121.png"); // Pixels
        imageMap.put("POL", "https://s2.coinmarketcap.com/static/img/coins/64x64/33524.png"); // Polygon Ecosystem Token
        imageMap.put("POLYX", "https://s2.coinmarketcap.com/static/img/coins/64x64/9574.png"); // Polymesh
        imageMap.put("POND", "https://s2.coinmarketcap.com/static/img/coins/64x64/7497.png"); // Marlin
        imageMap.put("PORTAL", "https://s2.coinmarketcap.com/static/img/coins/64x64/31596.png"); // Portal
        imageMap.put("PORTO", "https://s2.coinmarketcap.com/static/img/coins/64x64/11673.png"); // FC Porto Fan Token
        imageMap.put("POWR", "https://s2.coinmarketcap.com/static/img/coins/64x64/2132.png"); // Power Ledger
        imageMap.put("PROM", "https://s2.coinmarketcap.com/static/img/coins/64x64/4120.png"); // Prom
        imageMap.put("PROS", "https://s2.coinmarketcap.com/static/img/coins/64x64/8149.png"); // Prosper
        imageMap.put("PSG", "https://s2.coinmarketcap.com/static/img/coins/64x64/5228.png"); // Paris Saint-Germain Fan Token
        imageMap.put("PUNDIX", "https://s2.coinmarketcap.com/static/img/coins/64x64/9040.png"); // Pundi X
        imageMap.put("PYR", "https://s2.coinmarketcap.com/static/img/coins/64x64/9308.png"); // Vulcan Forged PYR
        imageMap.put("PYTH", "https://s2.coinmarketcap.com/static/img/coins/64x64/29124.png"); // Pyth Network
        imageMap.put("QI", "https://s2.coinmarketcap.com/static/img/coins/64x64/11843.png"); // Benqi
        imageMap.put("QKC", "https://s2.coinmarketcap.com/static/img/coins/64x64/2840.png"); // QuarkChain
        imageMap.put("QNT", "https://s2.coinmarketcap.com/static/img/coins/64x64/3155.png"); // Quant
        imageMap.put("QUICK", "https://s2.coinmarketcap.com/static/img/coins/64x64/8938.png"); // QuickSwap
        imageMap.put("RAD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6842.png"); // Radicle
        imageMap.put("RARE", "https://s2.coinmarketcap.com/static/img/coins/64x64/11962.png"); // SuperRare
        imageMap.put("RAY", "https://s2.coinmarketcap.com/static/img/coins/64x64/8526.png"); // Raydium
        imageMap.put("RED", "https://s2.coinmarketcap.com/static/img/coins/64x64/2783.png"); // RED
        imageMap.put("REI", "https://s2.coinmarketcap.com/static/img/coins/64x64/18548.png"); // REI Network
        imageMap.put("RENDER", "https://s2.coinmarketcap.com/static/img/coins/64x64/5690.png"); // Render
        imageMap.put("REQ", "https://s2.coinmarketcap.com/static/img/coins/64x64/2071.png"); // Request
        imageMap.put("RIF", "https://s2.coinmarketcap.com/static/img/coins/64x64/3701.png"); // RIF Token
        imageMap.put("RONIN", "https://s2.coinmarketcap.com/static/img/coins/64x64/18934.png"); // Ronin
        imageMap.put("ROSE", "https://s2.coinmarketcap.com/static/img/coins/64x64/7653.png"); // Oasis Network
        imageMap.put("RPL", "https://s2.coinmarketcap.com/static/img/coins/64x64/2943.png"); // Rocket Pool
        imageMap.put("RSR", "https://s2.coinmarketcap.com/static/img/coins/64x64/3964.png"); // Reserve Rights
        imageMap.put("RUNE", "https://s2.coinmarketcap.com/static/img/coins/64x64/8272.png"); // THORChain
        imageMap.put("RVN", "https://s2.coinmarketcap.com/static/img/coins/64x64/2577.png"); // Ravencoin
        imageMap.put("SAGA", "https://s2.coinmarketcap.com/static/img/coins/64x64/30991.png"); // Saga
        imageMap.put("SAND", "https://s2.coinmarketcap.com/static/img/coins/64x64/6210.png"); // The Sandbox
        imageMap.put("SANTOS", "https://s2.coinmarketcap.com/static/img/coins/64x64/11419.png"); // Santos FC Fan Token
        imageMap.put("SCRT", "https://s2.coinmarketcap.com/static/img/coins/64x64/5604.png"); // Secret
        imageMap.put("SEI", "https://s2.coinmarketcap.com/static/img/coins/64x64/29253.png"); // Sei
        imageMap.put("SFP", "https://s2.coinmarketcap.com/static/img/coins/64x64/8119.png"); // SafePal
        imageMap.put("SHIB", "https://s2.coinmarketcap.com/static/img/coins/64x64/5994.png"); // Shiba Inu
        imageMap.put("SKL", "https://s2.coinmarketcap.com/static/img/coins/64x64/9025.png"); // SKALE
        imageMap.put("SLP", "https://s2.coinmarketcap.com/static/img/coins/64x64/5824.png"); // Smooth Love Potion
        imageMap.put("SNT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1808.png"); // Status
        imageMap.put("SNX", "https://s2.coinmarketcap.com/static/img/coins/64x64/2586.png"); // Synthetix
        imageMap.put("SOL", "https://s2.coinmarketcap.com/static/img/coins/64x64/5426.png"); // Solana
        imageMap.put("SPELL", "https://s2.coinmarketcap.com/static/img/coins/64x64/11289.png"); // Spell Token
        imageMap.put("SSV", "https://s2.coinmarketcap.com/static/img/coins/64x64/12334.png"); // SSV Network
        imageMap.put("STEEM", "https://s2.coinmarketcap.com/static/img/coins/64x64/1230.png"); // Steem
        imageMap.put("STG", "https://s2.coinmarketcap.com/static/img/coins/64x64/18934.png"); // Stargate Finance
        imageMap.put("STORJ", "https://s2.coinmarketcap.com/static/img/coins/64x64/1772.png"); // Storj
        imageMap.put("STPT", "https://s2.coinmarketcap.com/static/img/coins/64x64/4006.png"); // Standard Tokenization Protocol
        imageMap.put("STRAX", "https://s2.coinmarketcap.com/static/img/coins/64x64/1343.png"); // Stratis
        imageMap.put("STRK", "https://s2.coinmarketcap.com/static/img/coins/64x64/19619.png"); // StarkNet
        imageMap.put("SUI", "https://s2.coinmarketcap.com/static/img/coins/64x64/20947.png"); // Sui
        imageMap.put("SUN", "https://s2.coinmarketcap.com/static/img/coins/64x64/10529.png"); // SUN
        imageMap.put("SUPER", "https://s2.coinmarketcap.com/static/img/coins/64x64/8290.png"); // SuperVerse
        imageMap.put("SUSHI", "https://s2.coinmarketcap.com/static/img/coins/64x64/6758.png"); // SushiSwap
        imageMap.put("SXP", "https://s2.coinmarketcap.com/static/img/coins/64x64/4279.png"); // Solar
        imageMap.put("SYN", "https://s2.coinmarketcap.com/static/img/coins/64x64/9476.png"); // Synapse
        imageMap.put("SYS", "https://s2.coinmarketcap.com/static/img/coins/64x64/541.png"); // Syscoin
        imageMap.put("T", "https://s2.coinmarketcap.com/static/img/coins/64x64/10536.png"); // Threshold
        imageMap.put("TAO", "https://s2.coinmarketcap.com/static/img/coins/64x64/24803.png"); // Bittensor
        imageMap.put("TFUEL", "https://s2.coinmarketcap.com/static/img/coins/64x64/3822.png"); // Theta Fuel
        imageMap.put("THE", "https://s2.coinmarketcap.com/static/img/coins/64x64/20986.png"); // The Protocol
        imageMap.put("THETA", "https://s2.coinmarketcap.com/static/img/coins/64x64/2416.png"); // Theta Network
        imageMap.put("TIA", "https://s2.coinmarketcap.com/static/img/coins/64x64/29191.png"); // Celestia
        imageMap.put("TKO", "https://s2.coinmarketcap.com/static/img/coins/64x64/8827.png"); // Tokocrypto
        imageMap.put("TLM", "https://s2.coinmarketcap.com/static/img/coins/64x64/9119.png"); // Alien Worlds
        imageMap.put("TON", "https://s2.coinmarketcap.com/static/img/coins/64x64/11419.png"); // Toncoin
        imageMap.put("TRB", "https://s2.coinmarketcap.com/static/img/coins/64x64/4944.png"); // Tellor
        imageMap.put("TROY", "https://s2.coinmarketcap.com/static/img/coins/64x64/5007.png"); // Troy
        imageMap.put("TRU", "https://s2.coinmarketcap.com/static/img/coins/64x64/5704.png"); // TrueFi
        imageMap.put("TRUMP", "https://s2.coinmarketcap.com/static/img/coins/64x64/28940.png"); // MAGA
        imageMap.put("TRX", "https://s2.coinmarketcap.com/static/img/coins/64x64/1958.png"); // TRON
        imageMap.put("TWT", "https://s2.coinmarketcap.com/static/img/coins/64x64/5964.png"); // Trust Wallet Token
        imageMap.put("UFT", "https://s2.coinmarketcap.com/static/img/coins/64x64/7533.png"); // UniLend
        imageMap.put("UMA", "https://s2.coinmarketcap.com/static/img/coins/64x64/5617.png"); // UMA
        imageMap.put("UNI", "https://s2.coinmarketcap.com/static/img/coins/64x64/7083.png"); // Uniswap
        imageMap.put("USTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/7129.png"); // TerraClassicUSD
        imageMap.put("VANA", "https://s2.coinmarketcap.com/static/img/coins/64x64/29499.png"); // Vanar Chain
        imageMap.put("VET", "https://s2.coinmarketcap.com/static/img/coins/64x64/3077.png"); // VeChain
        imageMap.put("VIB", "https://s2.coinmarketcap.com/static/img/coins/64x64/1982.png"); // Viberate
        imageMap.put("VIDT", "https://s2.coinmarketcap.com/static/img/coins/64x64/5321.png"); // VIDT DAO
        imageMap.put("VOXEL", "https://s2.coinmarketcap.com/static/img/coins/64x64/18934.png"); // Voxies
        imageMap.put("VTHO", "https://s2.coinmarketcap.com/static/img/coins/64x64/3012.png"); // VeThor Token
        imageMap.put("WAN", "https://s2.coinmarketcap.com/static/img/coins/64x64/2606.png"); // Wanchain
        imageMap.put("WAXP", "https://s2.coinmarketcap.com/static/img/coins/64x64/2300.png"); // WAX
        imageMap.put("WIF", "https://s2.coinmarketcap.com/static/img/coins/64x64/28830.png"); // dogwifhat
        imageMap.put("WIN", "https://s2.coinmarketcap.com/static/img/coins/64x64/5277.png"); // WINkLink
        imageMap.put("WING", "https://s2.coinmarketcap.com/static/img/coins/64x64/7208.png"); // Wing Finance
        imageMap.put("WLD", "https://s2.coinmarketcap.com/static/img/coins/64x64/29168.png"); // Worldcoin
        imageMap.put("WOO", "https://s2.coinmarketcap.com/static/img/coins/64x64/7501.png"); // WOO Network
        imageMap.put("XAI", "https://s2.coinmarketcap.com/static/img/coins/64x64/29869.png"); // Xai
        imageMap.put("XEC", "https://s2.coinmarketcap.com/static/img/coins/64x64/10791.png"); // eCash
        imageMap.put("XLM", "https://s2.coinmarketcap.com/static/img/coins/64x64/512.png"); // Stellar
        imageMap.put("XNO", "https://s2.coinmarketcap.com/static/img/coins/64x64/1567.png"); // Nano
        imageMap.put("XRP", "https://s2.coinmarketcap.com/static/img/coins/64x64/52.png"); // XRP
        imageMap.put("XTZ", "https://s2.coinmarketcap.com/static/img/coins/64x64/2011.png"); // Tezos
        imageMap.put("XVG", "https://s2.coinmarketcap.com/static/img/coins/64x64/693.png"); // Verge
        imageMap.put("XVS", "https://s2.coinmarketcap.com/static/img/coins/64x64/7288.png"); // Venus
        imageMap.put("YFI", "https://s2.coinmarketcap.com/static/img/coins/64x64/5864.png"); // yearn.finance
        imageMap.put("YGG", "https://s2.coinmarketcap.com/static/img/coins/64x64/13457.png"); // Yield Guild Games
        imageMap.put("ZEC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1437.png"); // Zcash
        imageMap.put("ZEN", "https://s2.coinmarketcap.com/static/img/coins/64x64/1698.png"); // Horizen
        imageMap.put("ZIL", "https://s2.coinmarketcap.com/static/img/coins/64x64/2469.png"); // Zilliqa
        imageMap.put("ZK", "https://s2.coinmarketcap.com/static/img/coins/64x64/30939.png"); // ZKsync
        imageMap.put("ZRO", "https://s2.coinmarketcap.com/static/img/coins/64x64/31285.png"); // LayerZero


        imageMap.put("1000CATFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31293.png"); // Cat in a Dogs World (1000CAT)
        imageMap.put("AAVEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/7278.png"); // AAVE
        imageMap.put("AERGO", "https://s2.coinmarketcap.com/static/img/coins/64x64/3637.png"); // Aergo
        imageMap.put("AEVOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31279.png"); // Aevo
        imageMap.put("AGLD", "https://s2.coinmarketcap.com/static/img/coins/64x64/12341.png"); // Adventure Gold
        imageMap.put("ALICE", "https://s2.coinmarketcap.com/static/img/coins/64x64/8766.png"); // MyNeighborAlice
        imageMap.put("ALPINE", "https://s2.coinmarketcap.com/static/img/coins/64x64/16352.png"); // Alpine F1 Team Fan Token
        imageMap.put("ANIMEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31299.png"); // Anime AI
        imageMap.put("ANKR", "https://s2.coinmarketcap.com/static/img/coins/64x64/3783.png"); // Ankr
        imageMap.put("API3", "https://s2.coinmarketcap.com/static/img/coins/64x64/7737.png"); // API3
        imageMap.put("ARDR", "https://s2.coinmarketcap.com/static/img/coins/64x64/1320.png"); // Ardor
        imageMap.put("ARKMFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/29226.png"); // Arkham
        imageMap.put("ASTR", "https://s2.coinmarketcap.com/static/img/coins/64x64/12885.png"); // Astar
        imageMap.put("AUCTIONFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/8602.png"); // Bounce
        imageMap.put("AXS", "https://s2.coinmarketcap.com/static/img/coins/64x64/6783.png"); // Axie Infinity
        imageMap.put("BAL", "https://s2.coinmarketcap.com/static/img/coins/64x64/5728.png"); // Balancer
        imageMap.put("BBFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31287.png"); // Bubblefong
        imageMap.put("BERAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31288.png"); // Bera Chain
        imageMap.put("BLUR", "https://s2.coinmarketcap.com/static/img/coins/64x64/23169.png"); // Blur
        imageMap.put("BNSOL", "https://s2.coinmarketcap.com/static/img/coins/64x64/28963.png"); // BonkSol
        imageMap.put("BONKFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/29316.png"); // Bonk
        imageMap.put("CHR", "https://s2.coinmarketcap.com/static/img/coins/64x64/3978.png"); // Chromia
        imageMap.put("CYBER", "https://s2.coinmarketcap.com/static/img/coins/64x64/28907.png"); // CyberConnect
        imageMap.put("D", "https://s2.coinmarketcap.com/static/img/coins/64x64/31297.png"); // Doge on Solana
        imageMap.put("DATA", "https://s2.coinmarketcap.com/static/img/coins/64x64/2437.png"); // Streamr
        imageMap.put("DOGEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/74.png"); // Dogecoin
        imageMap.put("DOGS", "https://s2.coinmarketcap.com/static/img/coins/64x64/31298.png"); // Dogecoin Solana
        imageMap.put("DOTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6636.png"); // Polkadot
        imageMap.put("DUSK", "https://s2.coinmarketcap.com/static/img/coins/64x64/4092.png"); // Dusk Network
        imageMap.put("EGLDFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6892.png"); // MultiversX
        imageMap.put("ENAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31289.png"); // Ethena
        imageMap.put("ENJ", "https://s2.coinmarketcap.com/static/img/coins/64x64/2130.png"); // Enjin Coin
        imageMap.put("ETHFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"); // Ethereum
        imageMap.put("ETHFI", "https://s2.coinmarketcap.com/static/img/coins/64x64/31280.png"); // EthereumFi
        imageMap.put("ETHT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"); // Ethereum Token
        imageMap.put("EUR", "https://s2.coinmarketcap.com/static/img/coins/64x64/5068.png"); // Euro Coin
        imageMap.put("EURI", "https://s2.coinmarketcap.com/static/img/coins/64x64/31281.png"); // Euro Invariant
        imageMap.put("FARM", "https://s2.coinmarketcap.com/static/img/coins/64x64/6859.png"); // Harvest Finance
        imageMap.put("FD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31282.png"); // Friend.tech Shares
        imageMap.put("FETFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/3773.png"); // Fetch.ai
        imageMap.put("FIDA", "https://s2.coinmarketcap.com/static/img/coins/64x64/7978.png"); // Bonfida
        imageMap.put("FIO", "https://s2.coinmarketcap.com/static/img/coins/64x64/5864.png"); // FIO Protocol
        imageMap.put("FIRO", "https://s2.coinmarketcap.com/static/img/coins/64x64/1414.png"); // Firo
        imageMap.put("FIS", "https://s2.coinmarketcap.com/static/img/coins/64x64/5882.png"); // Stafi
        imageMap.put("FLOW", "https://s2.coinmarketcap.com/static/img/coins/64x64/4558.png"); // Flow
        imageMap.put("FORTH", "https://s2.coinmarketcap.com/static/img/coins/64x64/9421.png"); // Ampleforth Governance
        imageMap.put("FUN", "https://s2.coinmarketcap.com/static/img/coins/64x64/1757.png"); // FunFair
        imageMap.put("GALAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/7080.png"); // Gala
        imageMap.put("GAS", "https://s2.coinmarketcap.com/static/img/coins/64x64/1785.png"); // Gas
        imageMap.put("GHST", "https://s2.coinmarketcap.com/static/img/coins/64x64/7046.png"); // Aavegotchi
        imageMap.put("GLMR", "https://s2.coinmarketcap.com/static/img/coins/64x64/6836.png"); // Moonbeam
        imageMap.put("GMT", "https://s2.coinmarketcap.com/static/img/coins/64x64/18069.png"); // STEPN
        imageMap.put("GMX", "https://s2.coinmarketcap.com/static/img/coins/64x64/11857.png"); // GMX
        imageMap.put("GNO", "https://s2.coinmarketcap.com/static/img/coins/64x64/1659.png"); // Gnosis
        imageMap.put("GNS", "https://s2.coinmarketcap.com/static/img/coins/64x64/21866.png"); // Gains Network
        imageMap.put("GPS", "https://s2.coinmarketcap.com/static/img/coins/64x64/31283.png"); // GPS Token
        imageMap.put("GPSFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31283.png"); // GPS Token (Future Derivative)
        imageMap.put("GRTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6719.png"); // The Graph
        imageMap.put("GTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/9913.png"); // Gitcoin
        imageMap.put("HARD", "https://s2.coinmarketcap.com/static/img/coins/64x64/7745.png"); // Kava Lend
        imageMap.put("HEI", "https://s2.coinmarketcap.com/static/img/coins/64x64/31284.png"); // Hei Network
        imageMap.put("HFT", "https://s2.coinmarketcap.com/static/img/coins/64x64/22291.png"); // Hashflow
        imageMap.put("HIGH", "https://s2.coinmarketcap.com/static/img/coins/64x64/11873.png"); // Highstreet
        imageMap.put("HIVE", "https://s2.coinmarketcap.com/static/img/coins/64x64/5370.png"); // Hive
        imageMap.put("HMSTR", "https://s2.coinmarketcap.com/static/img/coins/64x64/31285.png"); // Hamster Kombat
        imageMap.put("HOOK", "https://s2.coinmarketcap.com/static/img/coins/64x64/23095.png"); // Hooked Protocol
        imageMap.put("HOT", "https://s2.coinmarketcap.com/static/img/coins/64x64/2682.png"); // Holo
        imageMap.put("ICX", "https://s2.coinmarketcap.com/static/img/coins/64x64/2099.png"); // ICON
        imageMap.put("IDEX", "https://s2.coinmarketcap.com/static/img/coins/64x64/3920.png"); // IDEX
        imageMap.put("IO", "https://s2.coinmarketcap.com/static/img/coins/64x64/31286.png"); // IO Token
        imageMap.put("IOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31286.png"); // IO Token (Future Derivative)
        imageMap.put("IOST", "https://s2.coinmarketcap.com/static/img/coins/64x64/2405.png"); // IOST
        imageMap.put("IOTX", "https://s2.coinmarketcap.com/static/img/coins/64x64/2777.png"); // IoTeX
        imageMap.put("IQ", "https://s2.coinmarketcap.com/static/img/coins/64x64/2930.png"); // Everipedia
        imageMap.put("JST", "https://s2.coinmarketcap.com/static/img/coins/64x64/5488.png"); // JUST
        imageMap.put("JTO", "https://s2.coinmarketcap.com/static/img/coins/64x64/28925.png"); // Jito
        imageMap.put("JUV", "https://s2.coinmarketcap.com/static/img/coins/64x64/5229.png"); // Juventus Fan Token
        imageMap.put("KAIA", "https://s2.coinmarketcap.com/static/img/coins/64x64/31287.png"); // Kaia Network
        imageMap.put("KAITO", "https://s2.coinmarketcap.com/static/img/coins/64x64/31288.png"); // Kaito AI
        imageMap.put("KDA", "https://s2.coinmarketcap.com/static/img/coins/64x64/5647.png"); // Kadena
        imageMap.put("KMD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1521.png"); // Komodo
        imageMap.put("LAYER", "https://s2.coinmarketcap.com/static/img/coins/64x64/31289.png"); // LayerAI
        imageMap.put("LAZIO", "https://s2.coinmarketcap.com/static/img/coins/64x64/11894.png"); // Lazio Fan Token
        imageMap.put("LDFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31290.png"); // Lido DAO Future Derivative
        imageMap.put("LEVER", "https://s2.coinmarketcap.com/static/img/coins/64x64/20549.png"); // LeverFi
        imageMap.put("LINK", "https://s2.coinmarketcap.com/static/img/coins/64x64/1975.png"); // Chainlink
        imageMap.put("LISTA", "https://s2.coinmarketcap.com/static/img/coins/64x64/31291.png"); // Lista DAO
        imageMap.put("LSK", "https://s2.coinmarketcap.com/static/img/coins/64x64/1214.png"); // Lisk
        imageMap.put("LTCFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/2.png"); // Litecoin Future Derivative
        imageMap.put("MANTAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/29050.png"); // Manta Network Future Derivative
        imageMap.put("MDT", "https://s2.coinmarketcap.com/static/img/coins/64x64/2348.png"); // Measurable Data Token
        imageMap.put("ME", "https://s2.coinmarketcap.com/static/img/coins/64x64/31292.png"); // ME Token
        imageMap.put("MEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31292.png"); // ME Token Future Derivative
        imageMap.put("MTL", "https://s2.coinmarketcap.com/static/img/coins/64x64/1788.png"); // Metal DAO
        imageMap.put("NEARFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6535.png"); // NEAR Protocol Future Derivative
        imageMap.put("NEIRO", "https://s2.coinmarketcap.com/static/img/coins/64x64/31293.png"); // Neiro Network
        imageMap.put("NFP", "https://s2.coinmarketcap.com/static/img/coins/64x64/31294.png"); // NFPrompt
        imageMap.put("OPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/11840.png"); // Optimism Future Derivative
        imageMap.put("ORCA", "https://s2.coinmarketcap.com/static/img/coins/64x64/11165.png"); // Orca
        imageMap.put("ORCAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/11165.png"); // Orca Future Derivative
        imageMap.put("PENGU", "https://s2.coinmarketcap.com/static/img/coins/64x64/31295.png"); // Pengu Coin
        imageMap.put("PEPEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/24478.png"); // PEPE Future Derivative
        imageMap.put("PERF", "https://s2.coinmarketcap.com/static/img/coins/64x64/31296.png"); // Perfect Finance
        imageMap.put("PHB", "https://s2.coinmarketcap.com/static/img/coins/64x64/5964.png"); // Phoenix Global
        imageMap.put("PNUT", "https://s2.coinmarketcap.com/static/img/coins/64x64/31297.png"); // Peanut
        imageMap.put("POLFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31298.png"); // Polygon Future Derivative
        imageMap.put("RAYFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/8526.png"); // Raydium Future Derivative
        imageMap.put("RDNT", "https://s2.coinmarketcap.com/static/img/coins/64x64/22861.png"); // Radiant Capital
        imageMap.put("REZ", "https://s2.coinmarketcap.com/static/img/coins/64x64/31299.png"); // Rez Token
        imageMap.put("RLC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1637.png"); // iExec RLC
        imageMap.put("RONINFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/18954.png"); // Ronin Future Derivative
        imageMap.put("S", "https://s2.coinmarketcap.com/static/img/coins/64x64/31300.png"); // Singular Token
        imageMap.put("SAGAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31301.png"); // Saga Future Derivative
        imageMap.put("SANDFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/6210.png"); // The Sandbox Future Derivative
        imageMap.put("SC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1042.png"); // Siacoin
        imageMap.put("SCR", "https://s2.coinmarketcap.com/static/img/coins/64x64/31302.png"); // Scorum
        imageMap.put("SCRFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31302.png"); // Scorum Future Derivative
        imageMap.put("SEIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/23250.png"); // Sei Future Derivative
        imageMap.put("SHELL", "https://s2.coinmarketcap.com/static/img/coins/64x64/31303.png"); // Shell Token
        imageMap.put("SHIBFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/5994.png"); // Shiba Inu Future Derivative
        imageMap.put("SLF", "https://s2.coinmarketcap.com/static/img/coins/64x64/31304.png"); // Self Token
        imageMap.put("SOLFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1610.png"); // Solana Future Derivative
        imageMap.put("SOLVSTX", "https://s2.coinmarketcap.com/static/img/coins/64x64/31305.png"); // Solv STX
        imageMap.put("SUIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/20947.png"); // Sui Future Derivative
        imageMap.put("TAOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31306.png"); // Bittensor Future Derivative
        imageMap.put("TONFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/11419.png"); // Toncoin Future Derivative
        imageMap.put("TST", "https://s2.coinmarketcap.com/static/img/coins/64x64/31307.png"); // Test Token
        imageMap.put("TURBO", "https://s2.coinmarketcap.com/static/img/coins/64x64/31417.png"); // Turbo Token
        imageMap.put("USUAL", "https://s2.coinmarketcap.com/static/img/coins/64x64/31308.png"); // Usual Token
        imageMap.put("UTK", "https://s2.coinmarketcap.com/static/img/coins/64x64/2320.png"); // Utrust
        imageMap.put("VANRY", "https://s2.coinmarketcap.com/static/img/coins/64x64/31309.png"); // Vanaryst
        imageMap.put("VELODROME", "https://s2.coinmarketcap.com/static/img/coins/64x64/31310.png"); // Velodrome
        imageMap.put("VIC", "https://s2.coinmarketcap.com/static/img/coins/64x64/31311.png"); // VIC Token
        imageMap.put("W", "https://s2.coinmarketcap.com/static/img/coins/64x64/31312.png"); // W Token
        imageMap.put("WBETH", "https://s2.coinmarketcap.com/static/img/coins/64x64/22933.png"); // Wrapped Beacon ETH
        imageMap.put("WBTC", "https://s2.coinmarketcap.com/static/img/coins/64x64/3717.png"); // Wrapped Bitcoin
        imageMap.put("WIFFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31313.png"); // WIFF Future Derivative
        imageMap.put("WLDFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31314.png"); // World Future Derivative
        imageMap.put("XRPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/52.png"); // XRP Future Derivative
        imageMap.put("YGG", "https://s2.coinmarketcap.com/static/img/coins/64x64/10688.png"); // Yield Guild Games
        imageMap.put("ZKFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31315.png"); // zkSync Future Derivative

        imageMap.put("BCHFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1831.png"); // Bitcoin Cash Future Derivative
        imageMap.put("BNSOL", "https://s2.coinmarketcap.com/static/img/coins/64x64/18069.png"); // BonkSol
        imageMap.put("FD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31418.png"); // FD Token
        imageMap.put("FLOKIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/10429.png"); // Floki Future Derivative
        imageMap.put("G", "https://s2.coinmarketcap.com/static/img/coins/64x64/31419.png"); // G Token
        imageMap.put("HIFI", "https://s2.coinmarketcap.com/static/img/coins/64x64/9415.png"); // Hifi Finance
        imageMap.put("ICPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/8916.png"); // Internet Computer Future Derivative
        imageMap.put("KAITOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31420.png"); // Kaito Future Derivative
        imageMap.put("LDOFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/8000.png"); // Lido DAO Future Derivative
        imageMap.put("LINKFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1975.png"); // Chainlink Future Derivative
        imageMap.put("LRC", "https://s2.coinmarketcap.com/static/img/coins/64x64/1934.png"); // Loopring
        imageMap.put("LUMIA", "https://s2.coinmarketcap.com/static/img/coins/64x64/31421.png"); // Lumia Token
        imageMap.put("ME", "https://s2.coinmarketcap.com/static/img/coins/64x64/31292.png"); // ME Token
        imageMap.put("MLN", "https://s2.coinmarketcap.com/static/img/coins/64x64/1552.png"); // Enzyme Finance
        imageMap.put("NEIROFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31293.png"); // Neiro Network Future Derivative
        imageMap.put("NFPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31294.png"); // NFPrompt Future Derivative
        imageMap.put("NOTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/23223.png"); // Notcoin Future Derivative
        imageMap.put("OGN", "https://s2.coinmarketcap.com/static/img/coins/64x64/5117.png"); // Origin Protocol
        imageMap.put("OMNI", "https://s2.coinmarketcap.com/static/img/coins/64x64/83.png"); // Omni
        imageMap.put("PENDLEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/18069.png"); // Pendle Future Derivative
        imageMap.put("PORTALFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31422.png"); // Portal Future Derivative
        imageMap.put("QTUM", "https://s2.coinmarketcap.com/static/img/coins/64x64/1684.png"); // Qtum
        imageMap.put("SFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31423.png"); // SFD Token
        imageMap.put("SOLV", "https://s2.coinmarketcap.com/static/img/coins/64x64/31424.png"); // Solv Token
        imageMap.put("STX", "https://s2.coinmarketcap.com/static/img/coins/64x64/4847.png"); // Stacks
        imageMap.put("TIAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31425.png"); // Celestia Future Derivative
        imageMap.put("TNSR", "https://s2.coinmarketcap.com/static/img/coins/64x64/31426.png"); // Tensor Token
        imageMap.put("TRUMPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/21436.png"); // TrumpCoin Future Derivative
        imageMap.put("UNIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/7083.png"); // Uniswap Future Derivative
        imageMap.put("USUAL", "https://s2.coinmarketcap.com/static/img/coins/64x64/33979.png"); // Usual Token
        imageMap.put("WFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31427.png"); // W Future Derivative
        imageMap.put("ZRX", "https://s2.coinmarketcap.com/static/img/coins/64x64/1896.png"); // 0x Protocol

        imageMap.put("DOGSFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31295.png"); // DOGS Future Derivative
        imageMap.put("ETCFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/1321.png"); // Ethereum Classic Future Derivative
        imageMap.put("FLM", "https://s2.coinmarketcap.com/static/img/coins/64x64/7150.png"); // Flamingo
        imageMap.put("LOKA", "https://s2.coinmarketcap.com/static/img/coins/64x64/15664.png"); // League of Kingdoms Arena
        imageMap.put("XRPT", "https://s2.coinmarketcap.com/static/img/coins/64x64/31296.png"); // XRPT Token
        imageMap.put("REDFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31428.png"); // Red Future Derivative
        imageMap.put("PERP", "https://s2.coinmarketcap.com/static/img/coins/64x64/6950.png"); // Perpetual Protocol
        imageMap.put("ORDIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/31297.png"); // Ordi Future Derivative
        imageMap.put("ME", "https://s2.coinmarketcap.com/static/img/coins/64x64/31292.png"); // ME Token
        imageMap.put("FXS", "https://s2.coinmarketcap.com/static/img/coins/64x64/6953.png"); // Frax Share
        imageMap.put("USDT","https://s2.coinmarketcap.com/static/img/coins/64x64/825.png");
        imageMap.put("GLM", "https://s2.coinmarketcap.com/static/img/coins/64x64/1455.png"); // Golem (GLM)


        imageMap.put("BSW", "https://s2.coinmarketcap.com/static/img/coins/64x64/11007.png"); // Biswap (BSW)
        imageMap.put("EIGENFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/30494.png"); // No direct image found; may need manual update
        imageMap.put("FILFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/2280.png"); // No direct image found; may need manual update
        imageMap.put("GLM", "https://s2.coinmarketcap.com/static/img/coins/64x64/1455.png"); // Golem (GLM)
        imageMap.put("HBARFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/4642.png"); // No direct image found; may need manual update
        imageMap.put("IDEXFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/3928.png"); // No direct image found; may need manual update
        imageMap.put("JUPFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/29210.png"); // No direct image found; may need manual update
        imageMap.put("KNC", "https://s2.coinmarketcap.com/static/img/coins/64x64/9444.png"); // Kyber Network Crystal (KNC)
        imageMap.put("LISTAFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/21533.png"); // No direct image found; may need manual update
        imageMap.put("ME", "https://s2.coinmarketcap.com/static/img/coins/64x64/32197.png"); // No direct image found; may need manual update
        imageMap.put("MEMEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/28301.png"); // No direct image found; may need manual update
        imageMap.put("NOTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/28850.png"); // No direct image found; may need manual update
        imageMap.put("PEOPLEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/14806.png"); // No direct image found; may need manual update
        imageMap.put("PNUTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/33788.png"); // No direct image found; may need manual update
        imageMap.put("PYTHFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/28177.png"); // No direct image found; may need manual update
        imageMap.put("STXFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/4847.png"); // No direct image found; may need manual update
        imageMap.put("TSTFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/35647.png"); // No direct image found; may need manual update
        imageMap.put("ARFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/5632.png"); // No direct image found; may need manual update
        imageMap.put("BTCT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"); // Bitcoin Trust (BTCT)
        imageMap.put("MEFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/32197.png"); // No direct image found; may need manual update
        imageMap.put("SOLT", "https://s2.coinmarketcap.com/static/img/coins/64x64/1996.png"); // No direct image found; may need manual update
        imageMap.put("XAIFD", "https://s2.coinmarketcap.com/static/img/coins/64x64/28933.png"); // No direct image found; may need manual update





    }

    public static String getImageUrl(String symbol) {
        return imageMap.getOrDefault(symbol.toUpperCase(), "https://st4.depositphotos.com/14953852/22772/v/450/depositphotos_227724992-stock-illustration-image-available-icon-flat-vector.jpg");
    }



    // Getter for targetSymbols if needed
    public Set<String> getTargetSymbols() {
        return new HashSet<>(targetSymbols); // Return a copy to protect encapsulation
    }
}
