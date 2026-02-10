import { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [transactions, setTransactions] = useState([]);
  const [prediction, setPrediction] = useState("Loading AI analysis...");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 1. Fetch Transactions from JAVA
    fetch('http://localhost:8080/api/transactions')
      .then(response => response.json())
      .then(data => setTransactions(data))
      .catch(error => console.error("Java Error:", error));

    // 2. Fetch Prediction from PYTHON
    fetch('http://localhost:5000/predict')
      .then(response => response.json())
      .then(data => setPrediction(data.prediction))
      .catch(error => console.error("Python Error:", error));
      
    setLoading(false);
  }, []);

  return (
    <div className="container" style={{ padding: "40px", fontFamily: "Arial, sans-serif", maxWidth: "800px", margin: "0 auto" }}>
      
      {/* HEADER SECTION */}
      <h1 style={{ color: "#333" }}>Vantage Finance Dashboard</h1>
      
      {/* AI ALERT BOX */}
      <div style={{ 
        backgroundColor: "#fff3cd", 
        border: "1px solid #ffeeba", 
        color: "#856404", 
        padding: "20px", 
        borderRadius: "8px",
        marginBottom: "30px",
        boxShadow: "0 4px 6px rgba(0,0,0,0.1)"
      }}>
        <h3 style={{ margin: "0 0 10px 0" }}>🤖 AI Insight</h3>
        <p style={{ fontSize: "18px", margin: 0 }}>{prediction}</p>
      </div>

      {/* TRANSACTION TABLE */}
      {loading ? (
        <p>Loading Financial Data...</p>
      ) : (
        <table style={{ width: "100%", borderCollapse: "collapse", boxShadow: "0 2px 15px rgba(0,0,0,0.1)" }}>
          <thead>
            <tr style={{ backgroundColor: "#007ac3", color: "white", textAlign: "left" }}>
              <th style={{ padding: "15px" }}>Date</th>
              <th style={{ padding: "15px" }}>Description</th>
              <th style={{ padding: "15px" }}>Category</th>
              <th style={{ padding: "15px" }}>Amount (SEK)</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((t) => (
              <tr key={t.id} style={{ borderBottom: "1px solid #eee" }}>
                <td style={{ padding: "15px" }}>{t.date}</td>
                <td style={{ padding: "15px" }}>{t.description}</td>
                <td style={{ padding: "15px" }}>{t.category}</td>
                <td style={{ padding: "15px", fontWeight: "bold", color: t.amount > 0 ? "green" : "red" }}>
                  {t.amount} kr
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default App;