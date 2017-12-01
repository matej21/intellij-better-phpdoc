<?php

/**
 * @param array<DateTimeImmutable> $dates
 * @return iterable<string, DateTimeImmutable>
 */
function addYearToDates(array $dates)
{
    foreach ($dates as &$date) {
        $date = $date->modify('+1 year');
    }
    return $dates;
}

foreach (addYearToDates([]) as $date) {
    $date->format('Y-m-d');
}

