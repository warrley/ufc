import React from 'react';
import { Container, Paper, Typography } from '@mui/material';
import { BookingForm } from '../components/BookingForm';

export default function SchedulePage() {
  return (
    <Container maxWidth="sm" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom align="center">
          Barbearia - Agendamento
        </Typography>
        <Typography variant="body2" color="textSecondary" align="center" sx={{ mb: 4 }}>
          Agende seu próximo corte de cabelo ou barba.
        </Typography>

        <BookingForm />
      </Paper>
    </Container>
  );
}
