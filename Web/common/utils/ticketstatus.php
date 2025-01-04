<?php 

namespace TicketStatus;

function getTicketStatus($status) {
  $ticketStatuses = [
      0 => "Aberto",
      1 => "Em analise",
      2 => "A decorrer",
      3 => "Em processamento",
      4 => "Fechado",
  ];

  return $ticketStatuses[$status] ?? "Unknown status";
}
?>