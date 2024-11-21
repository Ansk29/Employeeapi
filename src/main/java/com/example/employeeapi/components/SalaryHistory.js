// src/components/SalaryHistory.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { getToken } from '../utils/auth';

const SalaryHistory = () => {
    const [salaryHistory, setSalaryHistory] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSalaryHistory = async () => {
            try {
                const token = getToken();
                const response = await axios.get('http://localhost:8080/api/salaries', {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setSalaryHistory(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchSalaryHistory();
    }, []);

    const handleDownload = (month, year) => {
        navigate(`/salary-slip/${month}/${year}`);
    };

    return (
        <div>
            <h2>Salary History</h2>
            <ul>
                {salaryHistory.map((salary) => (
                    <li key={salary.id}>
                        {salary.salaryDate}: ${salary.salaryAmount}{' '}
                        <button onClick={() => handleDownload(salary.salaryDate.month, salary.salaryDate.year)}>
                            Download Salary Slip
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default SalaryHistory;
