// src/components/SalarySlip.js

import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const SalarySlip = () => {
    const { month, year } = useParams();

    useEffect(() => {
        const downloadSalarySlip = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get(
                    `http://localhost:8080/api/salaries/salary-slip/pdf?month=${month}&year=${year}`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'application/pdf',
                        },
                        responseType: 'arraybuffer',
                    }
                );
                const blob = new Blob([response.data], { type: 'application/pdf' });
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = `SalarySlip_${month}_${year}.pdf`;
                link.click();
            } catch (error) {
                alert('Failed to download salary slip');
            }
        };

        downloadSalarySlip();
    }, [month, year]);

    return <div>Downloading salary slip...</div>;
};

export default SalarySlip;
