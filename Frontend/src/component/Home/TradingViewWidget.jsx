import React, { memo, useEffect, useRef } from "react";

const TradingViewWidget = memo(() => {
    const container = useRef(null); // Reference to widget container

    useEffect(() => {
        if (!container.current) return; // Prevent null reference
        container.current.innerHTML = ""; // Clear previous content

        // Remove old script if widget re-renders
        const existingScript = container.current.querySelector("script");
        if (existingScript) existingScript.remove();

        // Create new TradingView script
        const script = document.createElement("script");
        script.src = "https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js";
        script.type = "text/javascript";
        script.async = true;

        // TradingView widget settings
        script.innerHTML = `
        {
          "autosize": true,
          "symbol": "BINANCE:BTCUSD",
          "timezone": "Etc/UTC",
          "theme": "dark",
          "style": "1",
          "locale": "en",
          "backgroundColor": "#0d0d0d",
          "toolbar_bg": "#000000",
          "range": "ALL",
          "allow_symbol_change": true,
          "support_host": "https://www.tradingview.com"
        }`;

        container.current.appendChild(script); // Add script to container

        // Cleanup script on unmount
        return () => {
            if (script.parentNode) {
                script.parentNode.removeChild(script);
            }
        };
    }, []);

    return (
        <div
            className="tradingview-widget-container"
            ref={container}
            style={{ height: "100%", width: "100%" }}
        >
            <div
                className="tradingview-widget-container__widget"
                style={{ height: "calc(100% - 32px)", width: "100%" }}
            ></div>

            {/* TradingView Footer Text */}
            <div className="tradingview-widget-copyright">
                <a href="https://www.tradingview.com/" rel="noopener nofollow" target="_blank">
                    <span className="blue-text">Track all markets on TradingView</span>
                </a>
            </div>
        </div>
    );
});

export default TradingViewWidget;
