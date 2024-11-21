import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const Dashboard = () => {
    const [salaryHistory, setSalaryHistory] = useState([]);
    const [error, setError] = useState('');
    const history = useHistory();

    useEffect(() => {
        // Check if JWT token exists
        const token = localStorage.getItem('token');
        if (!token) {
            history.push('/login');
        } else {
            // Fetch the salary history
            fetchSalaryHistory(token);
        }
    }, [history]);

    const fetchSalaryHistory = async (token) => {
        try {
            const response = await axios.get('http://localhost:8080/api/salaries/history', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setSalaryHistory(response.data);
        } catch (err) {
            setError('Failed to fetch salary history');
        }
    };

    const downloadSalarySlip = async (month, year) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(`http://localhost:8080/api/salaries/salary-slip/pdf?month=${month}&year=${year}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                responseType: 'blob',
            });
            const blob = new Blob([response.data], { type: 'application/pdf' });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `SalarySlip_${month}_${year}.pdf`;
            link.click();
        } catch (err) {
            setError('Failed to download salary slip');
        }
    };

    return (
        <div>
            <h2>Dashboard</h2>
            {error && <p>{error}</p>}
            <h3>Salary History</h3>
            <ul>
                {salaryHistory.map((salary) => (
                    <li key={salary.id}>
                        {salary.salaryDate} - {salary.salaryAmount}
                        <button onClick={() => downloadSalarySlip(salary.salaryDate.split('-')[1], salary.salaryDate.split('-')[0])}>
                            Download Slip
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Dashboard;
