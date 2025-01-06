<?php

/** @var yii\web\View $this */

use yii\helpers\Html;

$this->title = 'About Us';
?>
<div class="site-about">
    <h1 class="about-title"><?= Html::encode($this->title) ?></h1>

    <div class="about-section">
        <p class="about-intro">
            Welcome to <strong>Fast Wheels</strong>, a platform designed to revolutionize the car rental experience.
            With the rise of global travel and technological advancements, Fast Wheels bridges the gap between those
            who need a car and those with vehicles to spare.
        </p>

        <h2 class="about-subtitle">Our Solution</h2>
        <ul class="about-list">
            <li><strong>Vehicle Owners:</strong> Earn passive income by renting out their unused vehicles.</li>
            <li><strong>Renters:</strong> Find high-quality, cost-effective vehicles tailored to their needs.</li>
        </ul>

        <h2 class="about-subtitle">Why Choose Fast Wheels?</h2>
        <ul class="about-list">
            <li><strong>Affordable Alternatives:</strong> Lower costs through peer-to-peer rentals, paying only platform and insurance fees.</li>
            <li><strong>Security:</strong> Comprehensive vehicle insurance ensures peace of mind for both parties.</li>
            <li><strong>Convenience:</strong> Advanced search filters, easy bookings, and secure payment options.</li>
        </ul>

        <h2 class="about-subtitle">Our Features</h2>
        <ul class="about-list">
            <li><strong>Vehicle Listing:</strong> Owners can list vehicles and track their reservations.</li>
            <li><strong>Advanced Search:</strong> Renters can filter vehicles by location, preferences, and price.</li>
            <li><strong>Favorites:</strong> Save your preferred vehicles for easy access later.</li>
            <li><strong>Reviews & Ratings:</strong> Share your experience and help others make informed decisions.</li>
            <li><strong>Comprehensive Insurance:</strong> Coverage for all rentals to guarantee safety and trust.</li>
        </ul>

        <p class="about-mission">
            At <strong>Fast Wheels</strong>, our mission is simple: to democratize vehicle rentals, making it easier, safer,
            and more accessible for everyone. <strong>Join us today</strong> and experience the future of car rentals!
        </p>
    </div>
</div>
